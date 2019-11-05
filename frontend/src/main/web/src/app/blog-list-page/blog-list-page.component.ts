import { Component, OnInit } from '@angular/core';
import { Blog_list_item } from '../_models/blog_list_item';
import { BlogService } from "../_services/blog.service"
import {Subscription} from "rxjs";
import {UserService} from "../_services";
import {ActivatedRoute, Route, Router} from "@angular/router";

@Component({
  selector: 'app-blog-list-page',
  templateUrl: './blog-list-page.component.html',
  styleUrls: ['./blog-list-page.component.css', '../fonts.css']
})

export class BlogListPageComponent implements OnInit {

  id:number;

  loading = false;
  lastRow = 0;
  maxPostsOnPage: number;
  step = 4;
  scrollDistance = 2;
  private sub: Subscription;
  author: string;
  filter:string;

  postsList: Blog_list_item[] = [];
  newChunk: Blog_list_item[];

  constructor(private blogService: BlogService, private userService: UserService, private router: ActivatedRoute,) {
  }

  ngOnInit() {
    if (this.router.snapshot.url.join('/').includes("blog/user/"))
      this.changeFilter("user");
    else
      this.changeFilter("subs");
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
      if (this.filter==="user")
      {
        this.sub = this.blogService.getBlogPostsByUserId(this.lastRow, this.step,this.id).subscribe(
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
              if (this.postsList.length>0) this.hideMessage();
              else this.showMessage();
            }
          },
          error1 => {
            this.loading = false;
          }
          )
      }
      else
      this.sub = this.blogService.getBlogPostsChunk(this.lastRow, this.step,this.filter).subscribe(
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
            if (this.postsList.length>0) this.hideMessage();
            else this.showMessage();
          }
        },
        error1 => {
          this.loading = false;
        }
      )
    }
  }

  filterBy(event, item) {
    if (item==="subs" || item==="all" || item==="my") {
      this.changeFilter(item);
      this.reloadList();
    }
  }

  reloadList(){
    this.postsList = [];
    this.lastRow = 0;
    this.loadBlogPostsChunk();
  }

  changeFilter(filter:string){
    this.filter=filter;
    document.getElementById('subs').setAttribute("class","filter-off");
    document.getElementById('all').setAttribute("class","filter-off");
    document.getElementById('my').setAttribute("class","filter-off");
    switch (filter) {
      case "subs":
        document.getElementById('subs').setAttribute("class","filter-on");
        break;
      case "all":
        document.getElementById('all').setAttribute("class","filter-on");
        break;
      case "my":
        document.getElementById('my').setAttribute("class","filter-on");
        break;
      case "user":
        this.id = this.router.snapshot.params['id'];
        break;
    }
  }

  itemDeletedHandler(deleted: Blog_list_item) {
    const index = this.postsList.indexOf(deleted, 0);
    if (index > -1) {
      this.postsList.splice(index, 1);
    }
    this.lastRow--;
  }

  hideMessage(){
    document.getElementById('message').setAttribute("style","display: none;");
  }

  showMessage(){
    document.getElementById('message').setAttribute("style","display: flex;");
  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe();
  }

}
