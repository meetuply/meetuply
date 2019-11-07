import {Component, OnInit} from '@angular/core';
import {BlogListItem} from '../_models/blogListItem';
import {BlogService} from "../_services/blog.service"
import {Subscription} from "rxjs";
import {UserService} from "../_services";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-blog-list-page',
  templateUrl: './blog-list-page.component.html',
  styleUrls: ['./blog-list-page.component.css', '../fonts.css']
})

export class BlogListPageComponent implements OnInit {

  id: number;

  loading = false;
  lastRow = 0;
  maxPostsOnPage: number;
  step = 4;
  scrollDistance = 2;
  author: string;
  filter: string;
  postsList: BlogListItem[] = [];
  newChunk: BlogListItem[];
  private sub: Subscription;

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
      if (this.filter === "user") {
        this.sub = this.blogService.getBlogPostsByUserId(this.lastRow, this.step, this.id).subscribe(
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
                      authorid = author.userId;
                    }
                  );
                  return new BlogListItem(item, username, photo, authorid)
                }
              ));
              this.postsList.push(...this.newChunk);
            }
          },
          error => {
            this.loading = false;
            console.log(error)
          }
        )
      } else
        this.sub = this.blogService.getBlogPostsChunk(this.lastRow, this.step, this.filter).subscribe(
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
                      authorid = author.userId;
                    }
                  );
                  return new BlogListItem(item, username, photo, authorid)
                }
              ));
              this.postsList.push(...this.newChunk);
            }
          },
          error => {
            this.loading = false;
            console.log(error)
          }
        )
    }
  }

  filterBy(event, item) {
    if (item === "subs" || item === "all" || item === "my") {
      this.changeFilter(item);
      this.reloadList();
    }
  }

  reloadList() {
    this.postsList = [];
    this.lastRow = 0;
    this.loadBlogPostsChunk();
  }

  changeFilter(filter: string) {
    this.filter = filter;
    document.getElementById('subs').setAttribute("class", "filter-off");
    document.getElementById('all').setAttribute("class", "filter-off");
    document.getElementById('my').setAttribute("class", "filter-off");
    switch (filter) {
      case "subs":
        document.getElementById('subs').setAttribute("class", "filter-on");
        break;
      case "all":
        document.getElementById('all').setAttribute("class", "filter-on");
        break;
      case "my":
        document.getElementById('my').setAttribute("class", "filter-on");
        break;
      case "user":
        this.id = this.router.snapshot.params['id'];
        break;
    }
  }

  itemDeletedHandler(deleted: BlogListItem) {
    const index = this.postsList.indexOf(deleted, 0);
    if (index > -1) {
      this.postsList.splice(index, 1);
    }
    this.lastRow--;
  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe();
  }

}
