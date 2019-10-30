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
  error = null;
  lastRow = 0;
  maxCommentsOnPage: number;
  step = 4;
  scrollDistance = 2;
  commentAuthor: string;
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
      this.sub = this.blogService.getBlogCommentsChunk(this.blogpost.blogPostId, this.lastRow, this.step).subscribe(
        async data => {
          this.loading = false;
          if (data) {
            this.lastRow += data.length;
            this.newChunk = await Promise.all(data.map(async item => {
                let username = "";
                let photo = "";
                await this.userService.get(item.authorId).toPromise().then(
                  author => {
                    username = author.firstName + " " + author.lastName;
                    photo = author.photo;
                  }
                );
                return new Blog_comment_item(item, username, photo)
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

  loadBlogPost(id: number) {
    this.loading = true;
    this.sub = this.blogService.getBlogPost(id).subscribe(
      data => {
        this.blogpost = data;
        this.loading = false;
        // console.log(this.blogpost.blogPostContent);
        this.getAuthorInfo(data['authorId']);

        this.loadBlogCommentsChunk();
      },
      error => {
        // this.alertService.error(error);
        this.loading = false;
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
      }
    );
  }

  submitComment($event) {
    var datetime = new Date(Date.now());

    var comment: BlogComment = {
      blogCommentId: 0,
      blogCommentContent: this.new_comment,
      authorId: this.userService.currentUser.userId,
      postId: this.id,
      time: datetime
    };

    this.blogService.createBlogComment(comment).subscribe(data => {
      if (data == null) {
        //refresh
      }
    }, error => {
      alert(error)
    });
  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe;
  }

  goBack() {
    this._location.back();
  }

  formatContent(str: string) {
    return str.replace(/(?:\r\n|\r|\n)/g, '<br/>');
  }

}
