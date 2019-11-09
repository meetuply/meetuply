import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, Router} from "@angular/router";

import {User} from '../_models'
import {UserService} from '../_services/user.service'

import {ChatService} from '../_services/chat.service'
import {Achievement} from "../_models/achievement";
import {AchievementService} from "../_services/achievement.service";
import {RatingService} from "../_services/rating.service";
import {BlogService} from "../_services/blog.service";
import {BlogListItem} from "../_models/blogListItem";
import {Subscription} from "rxjs";
import {Meetup} from "../_models/meetup";
import {StateService} from "../_services/state.service";
import {MeetupService} from "../_services/meetup.service";
import {FeedbackService} from "../_services/feedback.service";
import {Feedback} from "../_models/feedback";
import {BlogCommentItem} from "../_models/blogCommentItem";
import {FeedbackListItem} from "../_models/feedback-list-item";


@Component({
  selector: 'app-speaker-page',
  templateUrl: './speaker-page.component.html',
  styleUrls: ['./speaker-page.component.css', '../fonts.css']
})
export class SpeakerPageComponent implements OnInit {

  id: number;
  user: User;
  followers: number[];
  languages: string[];
  rate: number;
  following: boolean;
  needsFeedback: boolean;
  achievementList: Achievement[] = [];
  futureMeetups: Meetup[] = [];
  pastMeetups: Meetup[] = [];
  feedback: FeedbackListItem[] = [];
  error;
  loading: boolean;
  lastPost: BlogListItem;
  lastPostDefined: boolean = false;
  viewAllFuture = false;
  viewAllPast = false;
  viewAllFeedback=false;
  currentUser: number;
  commonRoomId: number;
  meetup: Meetup;
  private sub: Subscription;

  test:boolean;
  test1:number[];

  constructor(private _location: Location, private router: Router,
              public userService: UserService, private route: ActivatedRoute,
              private achievementService: AchievementService,
              private ratingService: RatingService, private chatService: ChatService,
              public stateService: StateService,
              private meetupService: MeetupService,
              private blogService: BlogService,
              private feedbackService: FeedbackService) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.loadCommonRoom(this.id, this.userService.currentUser.userId);
    this.loadUser(this.id);
    this.loadRating(this.id);
    this.loadFollowers(this.id);
    this.loadLanguages(this.id);
    this.loadAchievements(this.id);
    this.loadMeetups();
    this.loadFeedback(this.id);
    this.hasToLeaveFeedback();
  }

  goBack() {
    this._location.back();
  }

  loadUser(id: number) {
    this.loading = true;
    this.userService.get(id).subscribe(user =>
      this.user = user
    );

    this.loading = true;
    this.sub = this.userService.get(id).subscribe(
      async user => {
        this.loading = false;
        if (user) {
          this.user = user;
          await this.loadLastPost(id);
        }
      });
  }

  loadRating(id: number) {
    this.ratingService.getUserRatingAvg(id).subscribe(res => {
      this.rate = res;
    })
  }

  loadFeedback(id:number){
    this.feedbackService.getFeedbackTo(id).subscribe(
      async data => {
        if (data) {
          this.feedback = await Promise.all(data.map(async item => {
              let username = "";
              let photo = "";
              let authorid = 0;
              let rating: number = 0;

              await this.userService.get(item.feedbackBy).toPromise().then(
                async author => {
                  username = author.firstName + " " + author.lastName;
                  photo = author.photo;
                  authorid=author.userId;

                  await this.ratingService.getRatingByTo(authorid,id).toPromise().then(rate => {
                    rating = rate.value;
                  });
                }
              );

              return new FeedbackListItem(item, rating, username, photo, authorid)
            }
          ));
        }
      },
      error => {
        console.log(error);
      }
    )
  }

  loadFollowers(id: number) {
    this.userService.getUserFollowers(id).subscribe(res => {
      this.followers = res;
      this.following = (this.followers.indexOf(this.userService.currentUser.userId) != -1)
    });
  }

  loadLanguages(id: number) {
    this.userService.getUserLanguages(id).subscribe(res =>
      this.languages = res.map(l => l.name)
    );
  }

  loadLastPost(id: number) {
    this.blogService.getBlogPostsByUserId(0, 1, id).subscribe(posts => {
      if (posts.length > 0) {
        this.lastPost = new BlogListItem(posts.pop(),
          this.user.firstName + " " + this.user.lastName,
          this.user.photo, this.id);
        this.lastPostDefined = true;
      }
    })
  }

  loadCommonRoom(id1: number, id2: number) {
    this.chatService.haveCommonRoom(id1, id2).subscribe(
      common => this.commonRoomId = common
    )
  }

  message() {
    if (this.id != this.userService.currentUser.userId) {
      if (this.commonRoomId == -1) {
        this.chatService.createCommmonRoom(this.id, this.userService.currentUser.userId).subscribe(
          room => this.router.navigateByUrl("/chats/" + room)
        )
      } else {
        this.router.navigateByUrl("/chats/" + this.commonRoomId)
      }
    }
  }

  loadMeetups() {
    this.meetupService.getFutureMeetups(this.id).toPromise().then(
      data => {
        this.futureMeetups = data
      },
      error => {
        console.log(error)
      }
    );
    this.meetupService.getPastMeetups(this.id).toPromise().then(
      data => {
        this.pastMeetups = data
      },
      error => {
        console.log(error)
      })
  }

  changeViewAllFuture(event) {
    this.viewAllFuture = !this.viewAllFuture;
  }

  changeViewAllPast(event) {
    this.viewAllPast = !this.viewAllPast;
  }

  changeViewAllFeedback(event) {
    this.viewAllFeedback = !this.viewAllFeedback;
  }

  loadAchievements(id: number) {
    this.achievementService.getUserAchievements(id).toPromise().then(
      achievements => {
        this.achievementList = achievements;
      }
    )
  }

  followText(): string {
    if (this.following === true) {
      return "Unfollow";
    }
    return "Follow";
  }

  followType(): number {
    if (this.following === true) {
      return 2;
    }
    return 1;
  }

  deactivationText(): string {
    if (this.user.deactivated === true) {
      return "Activate";
    }
    return "Deactivate";
  }

  deactivationType(): number {
    if (this.user.deactivated === true) {
      return 1;
    }
    return 2;
  }

  deactivationButtonClicked(event) {
    if (!this.user.deactivated) {
      this.userService.deactivate(this.id).subscribe(
        data => {
          this.loadUser(this.id);
        },
        error => {
          this.error = error;
        }
      );
    } else {
      this.userService.reactivate(this.id).subscribe(
        data => {
          this.loadUser(this.id);
        },
        error => {
          this.error = error;
        }
      );
    }
  }

  followButtonClicked(event) {
    if (this.following)
      this.userService.unfollow(this.id).subscribe(
        data => {
          this.following = false;
          this.loadFollowers(this.id);
        },
        error => {
          this.error = error;
        }
      );
    else
      this.userService.follow(this.id).subscribe(
        data => {
          this.following = true;
          this.loadFollowers(this.id);
        },
        error => {
          this.error = error;
        }
      );
  }

  blogDeletedHandler(deleted: BlogListItem) {
    this.lastPost = null;
    this.loadLastPost(this.id);
  }

  isCurrentUser() {
    return this.userService.currentUser.userId == this.id
  }

  isAdmin() {
    return this.userService.currentUser.role.roleName === 'admin';
  }

  hasToLeaveFeedback() {
    this.feedbackService.getWaitingFeedback(this.userService.currentUser.userId).subscribe(data => {
      this.needsFeedback = data.some(x => x == this.id);
    });
  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe();
  }


}
