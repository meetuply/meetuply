import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from '../_services/user.service'
import {Subscription} from "rxjs";
import {Meetup} from "../_models/meetup";
import {MeetupService} from "../_services/meetup.service";
import {BlogListItem} from "../_models/blogListItem";
import {BlogService} from "../_services/blog.service";
import {StateService} from "../_services/state.service";
import {MeetupListItem} from "../_models/meetupListItem";


@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.css', '../fonts.css']
})
export class DashboardPageComponent implements OnInit {

  id: number;
  soonPeriodDaysFromNow = 3;
  soonMeetups: MeetupListItem[] = [];
  userMeetups: Meetup[] = [];
  lastNotifications = [];
  blogPosts: BlogListItem[] = [];
  viewAllSoon = false;
  viewAllUser = false;
  error;
  private sub: Subscription;

  constructor(private router: Router,
              public userService: UserService,
              private meetupService: MeetupService,
              private blogService: BlogService,
              public stateService: StateService) {
  }

  ngOnInit() {
    this.id = this.userService.currentUser.userId;
    this.loadBlogPosts();
    this.loadMeetups();
  }

  loadMeetups(){
    this.meetupService.getFutureMeetups(this.id).toPromise().then(
      data => {
        this.userMeetups = data;
      },
      error => {
        console.log(error)
      }
    );
    this.meetupService.getSoonMeetups(this.id, this.soonPeriodDaysFromNow).toPromise().then(
      data => {
        this.soonMeetups = data;
      },
      error => {
        console.log(error)
      })
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
              return new BlogListItem(item, username, photo, authorid)
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

  changeViewAllSoon(){
    this.viewAllSoon = !this.viewAllSoon;
  }

  changeViewAllUser(){
    this.viewAllUser = !this.viewAllUser;
  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe();
  }
}
