import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from '../_services/user.service'
import {Subscription} from "rxjs";
import {Meetup} from "../_models/meetup";
import {MeetupService} from "../_services/meetup.service";
import {Blog_list_item} from "../_models/blog_list_item";
import {BlogService} from "../_services/blog.service";


@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.css', '../fonts.css']
})
export class DashboardPageComponent implements OnInit {

  futureMeetups: Meetup[] = [];
  myMeetups: Meetup[] = [];
  blogPosts: Blog_list_item[] = [];
  error;
  private sub: Subscription;

  constructor(private router: Router,
              public userService: UserService,
              private meetupService: MeetupService,
              private blogService: BlogService) {
  }

  ngOnInit() {
    this.loadBlogPosts();
  }

  loadBlogPosts() {
    this.sub = this.blogService.getBlogPostsChunk(0, 2, "subs").subscribe(
      async data => {
        if (data) {
          this.blogPosts = await Promise.all(data.map(async item => {
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
              return new Blog_list_item(item, username, photo, authorid)
            }
          ));
          console.log(this.blogPosts);
        }
      },
      error => {
        console.log(error);
      }
    );


  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe();
  }
}
