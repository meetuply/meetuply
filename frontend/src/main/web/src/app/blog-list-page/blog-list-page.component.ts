import { Component, OnInit } from '@angular/core';
import { Blog_list_item } from '../_models/blog_list_item';
import { BlogService } from "../_services/blog.service"
import {Subscription} from "rxjs";
import {UserService} from "../_services";

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

  constructor(private blogService: BlogService, private userService: UserService) {
  }

  ngOnInit() {
    this.maxPostsOnPage = 50;
    this.loadBlogPostsChunk();
  }

  onScrollDown() {
    // console.log(this.postsList.length);
    this.loadBlogPostsChunk();
  }

  loadBlogPostsChunk() {
    if (this.lastRow < this.maxPostsOnPage) {
      this.loading = true;
      this.sub = this.blogService.getBlogPostsChunk(this.lastRow, this.step).subscribe(
        async data => {
          this.loading = false;
          if (data){
            this.lastRow += data.length;
            this.newChunk = await Promise.all(data.map( async item => {
                let username = "";
                let photo = "";
                let authorid=0;
                await this.userService.get(item.authorId).toPromise().then(
                  author => {
                    username = author.firstName + " " + author.lastName;
                    photo = author.photo;
                    authorid=author.userId;
                  }
                );
                return new Blog_list_item(item, username, photo,authorid)
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

  filterBy(event, item) {
    switch(item){
      case 0:
        alert("filerere");
        break;
      case 1:
        break;
      case 2:
        break;
    }
  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe();
  }

}
