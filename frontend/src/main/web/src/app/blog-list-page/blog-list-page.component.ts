import { Component, OnInit } from '@angular/core';
import { Blog_list_item } from '../_models/blog_list_item';
import {HttpClient} from "@angular/common/http";
import { BlogService } from "../_services/blog.service"
import {Subscription} from "rxjs";

@Component({
  selector: 'app-blog-list-page',
  templateUrl: './blog-list-page.component.html',
  styleUrls: ['./blog-list-page.component.css', '../fonts.css']
})

export class BlogListPageComponent implements OnInit {

  loading = false;
  lastRow = 0;
  maxPostsOnPage: number;
  step = 4;
  scrollDistance = 2;
  private sub: Subscription;
  author: string;

  postsList: Blog_list_item[] = [];
  newChunk: Blog_list_item[];

  constructor(private blogService: BlogService) {
  }

  ngOnInit() {
    this.maxPostsOnPage = 50;
    this.loadBlogPostsChunk();
  }

  onScrollDown() {
    console.log(this.postsList.length);
    this.loadBlogPostsChunk();
  }

  loadBlogPostsChunk() {
    console.log("LAST ROW: ");
    console.log(this.lastRow);
    if (this.lastRow < this.maxPostsOnPage) {
      this.loading = true;
      this.sub = this.blogService.getBlogPostsChunk(this.lastRow, this.step).subscribe(
        async data => {
          this.loading = false;
          if (data){
            this.lastRow += data.length;
            this.newChunk = await Promise.all(data.map( async item => {
                // console.log("postid "+item.blogPostId);
                // console.log("authorid "+ item.author);
                let username = item.author.firstName + " " + item.author.lastName;
                let photo = item.author.photo;
                return new Blog_list_item(item, username, photo)
              }
            ));
            this.postsList.push(...this.newChunk);
          }
        },
        error1 => {
          this.loading = false;
        }
      )
    }
  }



}
