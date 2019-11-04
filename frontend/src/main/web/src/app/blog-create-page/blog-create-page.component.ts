import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import {BlogService} from "../_services/blog.service";
import {BlogPost} from "../_models";
import {UserService} from "../_services";
import {Location} from "@angular/common";

@Component({
  selector: 'app-blog-create-page',
  templateUrl: './blog-create-page.component.html',
  styleUrls: ['./blog-create-page.component.less']
})
export class BlogCreatePageComponent implements OnInit {

  constructor(private _location: Location, private router: Router,
              private blogService: BlogService, private userService: UserService,) { }

  post_title: string;
  post_content: string;

  submit($event) {
    window.document.getElementById("title-error").setAttribute("style","display:none;");
    window.document.getElementById("content-error").setAttribute("style","display:none;");
    if (this.post_title.length<=0){
      window.document.getElementById("title-error").setAttribute("style","display:block;");
    }
    if (this.post_content.length<=0){
      window.document.getElementById("content-error").setAttribute("style","display:block");
    }
    if (this.post_title.length>0 && this.post_content.length>0) {
      let datetime = new Date(Date.now());

      let blogpost: BlogPost = {
        blogPostId: 0,
        blogPostTitle: this.post_title,
        blogPostContent: this.post_content,
        authorId: this.userService.currentUser.userId,
        time: datetime
      }

      this.blogService.createBlogPost(blogpost).subscribe(data => {
        if (data == null) {
          //supposed to redirect somewhere
          this.goBack();
        }
      }, error => {
        console.log(error);
      });


    }
  }

  ngOnInit() {
    this.post_content="";
    this.post_title="";
  }

  goBack() {
    this._location.back();
  }

}
