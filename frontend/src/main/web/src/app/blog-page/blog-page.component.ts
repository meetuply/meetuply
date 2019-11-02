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
  loading = false;
  id: number;
  author: string;
  authorPhoto: string;
  authorId: number;
  error = null;
  lastRow = 0;
  maxCommentsOnPage: number;
  step = 4;
  scrollDistance = 2;
  commentsList: Blog_comment_item[] = [];
  newChunk: Blog_comment_item[];
  new_comment: string;
  private sub: Subscription;

  constructor(private userService: UserService, private blogService: BlogService,
              private _location: Location, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.loadBlogPost(this.id);
    this.maxCommentsOnPage = 50;
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
        error1 => {
          this.loading = false;
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
        this.loading = false;
        this.getAuthorInfo(data['authorId']);
      },
      error => {
        // this.alertService.error(error);
        this.loading = false;
      }
    );
    this.loadBlogCommentsChunk();
  }

  getAuthorInfo(id: number) {
    this.loading = true;
    this.userService.get(id).subscribe(
      data => {
        this.loading = false;
        this.author = data['firstName'] + " " + data['lastName'];
        this.authorPhoto = data['photo']
      }
    );
  }

  submitComment($event) {
    let comment: BlogComment = {
      blogCommentContent: this.new_comment,
      authorId: this.userService.currentUser.userId,
      postId: this.id
    };

    this.blogService.createBlogComment(comment).subscribe(data => {
      if (data == null) {
        //refresh
        this.reloadComments();
      }
    }, error => {
      alert(error)
    });
  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe();
  }

  goBack() {
    this._location.back();
  }

}
