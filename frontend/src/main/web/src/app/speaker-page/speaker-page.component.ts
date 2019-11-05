import {Component, OnInit} from '@angular/core';
import {History} from '../history'
import {Feedback} from "../feedback"
import {Location} from '@angular/common';
import {ActivatedRoute, Router} from "@angular/router";

import {User} from '../_models'
import {UserService} from '../_services/user.service'

import {ChatService} from '../_services/chat.service'
import {Achievement} from "../_models/achievement";
import {AchievementService} from "../_services/achievement.service";
import {MeetupListItem} from "../_models/meetupListItem";
import {Subscription} from "rxjs";
import {RatingService} from "../_services/rating.service";
import {Meetup} from "../_models/meetup";
import {StateService} from "../_services/state.service";
import {MeetupService} from "../_services/meetup.service";


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
  achievementList: Achievement[] = [];
  futureMeetups: Meetup[] = [];
  pastMeetups: Meetup[] = [];
  feedback= [];
  error;
  viewAllFuture = false;
  currentUser: number;
  commonRoomId: number;
  meetup: Meetup;

  constructor(private _location: Location, private router: Router,
              public userService: UserService, private route: ActivatedRoute,
              private achievementService: AchievementService,
              private ratingService: RatingService, private chatService: ChatService,
              public stateService: StateService,
              private meetupService: MeetupService) {
  }

  ngOnInit() {
    this.currentUser = this.userService.currentUser.userId;
    this.id = this.route.snapshot.params['id'];
    this.loadCommonRoom(this.id, this.userService.currentUser.userId);
    this.loadUser(this.id);
    this.loadFollowers(this.id);
    this.loadLanguages(this.id);
    this.loadAchievements(this.id);
    this.loadRating(this.id);
    this.loadMeetups();
  }

  goBack() {
    this._location.back();

  }

  loadUser(id: number) {
    this.userService.get(id).subscribe(user =>
      this.user = user
    );
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

  loadMeetups(){
    this.meetupService.getFutureMeetups(this.id).toPromise().then(
    data => this.futureMeetups = data
    )
  }

  changeViewAllFuture(event){
    this.viewAllFuture = !this.viewAllFuture;
  }

  loadAchievements(id: number) {
    this.achievementService.getUserAchievements(id).toPromise().then(
      achievements => {
        this.achievementList = achievements;
      }
    )
  }

  loadRating(id: number) {
    this.ratingService.getUserRatingAvg(id);
  }

  followText(): string {
    if (this.following === true) {
      return "unfollow";
    }
    return "follow";
  }

  followType(): number {
    if (this.following === true) {
      return 2;
    }
    return 1;
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
}
