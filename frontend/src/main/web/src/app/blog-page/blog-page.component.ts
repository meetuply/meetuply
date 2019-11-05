import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";
import {UserService} from "../_services";
import {BlogService} from "../_services/blog.service";
import {BlogPost} from "../_models";
import {Blog_comment_item} from "../_models/blog_comment_item";
import {BlogComment} from "../_models/comment";

@Component({
  selector: 'app-blog-page',
  templateUrl: './blog-page.component.html',
  styleUrls: ['./blog-page.component.css', '../fonts.css']
})
export class BlogPageComponent implements OnInit {

  blogpost: BlogPost = new BlogPost();
  id: number;
  author: string;
  authorPhoto: string;
  authorId: number;
  time: string;

  lastRow = 0;
  maxCommentsOnPage: number;
  step = 10;
  scrollDistance = 2;

  commentsList: Blog_comment_item[] = [];
  newChunk: Blog_comment_item[];
  new_comment: string;

  private sub: Subscription;
  loading = false;
  error = null;

  constructor(private userService: UserService, private blogService: BlogService,
              private _location: Location, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.loadBlogPost(this.id);
    this.maxCommentsOnPage = 50;
    this.new_comment="";
  }

  onScrollDown() {
    this.loadBlogCommentsChunk();
  }

  loadBlogCommentsChunk() {
    if (this.lastRow < this.maxCommentsOnPage) {
      this.loading = true;
      this.sub = this.blogService.getBlogCommentsChunk(this.id, this.lastRow, this.step).subscribe(
        async data => {
          this.loading = false;
          if (data) {
            this.lastRow += data.length;
            this.newChunk = await Promise.all(data.map(async item => {
                let username = "";
                let photo = "";
                let authorid = 0;
                await this.userService.get(item.authorId).toPromise().then(
                  author => {
                    username = author.firstName + " " + author.lastName;
                    photo = author.photo;
                    authorid=author.userId;
                  }
                );
                return new Blog_comment_item(item, username, photo, authorid)
              }
            ));
            this.commentsList.push(...this.newChunk);
          }
        },
        error => {
          this.loading = false;
          console.log(error);
        }
      )
    }
  }

  reloadComments(){
    this.commentsList=[];
    this.lastRow=0;
    this.loadBlogCommentsChunk();
  }

  loadBlogPost(id: number) {
    this.loading = true;
    this.sub = this.blogService.getBlogPost(id).subscribe(
      data => {
        this.blogpost = data;
        this.blogpost.blogPostContent=this.blogpost.blogPostContent.replace(/(?:\r\n|\r|\n)/g, '<br/>');
        this.time=this.blogpost.time.toString().replace("T"," ");
        this.authorId=this.blogpost.authorId;
        this.loading = false;
        this.getAuthorInfo(data['authorId']);
        this.loadBlogCommentsChunk();
      },
      error => {
        this.loading = false;
        console.log(error);
      }
    );
  }

  getAuthorInfo(id: number) {
    this.loading = true;
    this.userService.get(id).subscribe(
      data => {
        this.loading = false;
        this.author = data['firstName'] + " " + data['lastName'];
        this.authorPhoto = data['photo']
      }, error => {
        console.log(error)
      }
    );
  }

  submitComment($event) {
    if (this.new_comment.length>0)
    {
      let datetime = new Date(Date.now());

      let comment: BlogComment = {
        blogCommentId: 0,
        blogCommentContent: this.new_comment,
        authorId: this.userService.currentUser.userId,
        postId: this.id,
        time: datetime
      };

      this.blogService.createBlogComment(comment).subscribe(async data => {
        if (data == null) {
          //refresh
          this.reloadComments();
          this.new_comment="";
        }
      }, error => {
        console.log(error)
      });

      window.document.getElementById("form-error").setAttribute("style","display:none;");
    }
    else{
      window.document.getElementById("form-error").setAttribute("style","display:block;");
    }
  }

  itemDeletedHandler(deleted: Blog_comment_item) {
    const index = this.commentsList.indexOf(deleted, 0);
    if (index > -1) {
      this.commentsList.splice(index, 1);
    }
    this.lastRow--;
  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe();
  }

  goBack() {
    this._location.back();
  }

}
