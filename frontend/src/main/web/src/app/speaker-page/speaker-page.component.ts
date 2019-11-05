import { Component, OnInit } from '@angular/core';
import { History } from '../history'
import { Feedback } from "../feedback"
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from "@angular/router";

import { User} from '../_models'
import { UserService } from '../_services/user.service'

import { ChatService } from '../_services/chat.service'
import { Achievement } from "../_models/achievement";
import { AchievementService } from "../_services/achievement.service";
import { MeetupListItem } from "../_models/meetupListItem";
import { RatingService } from "../_services/rating.service";
import {BlogService} from "../_services/blog.service";
import {Blog_list_item} from "../_models/blog_list_item";
import {Subscription} from "rxjs";


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
  error;
  loading: boolean;
  private sub: Subscription;

  lastPost: Blog_list_item;

  commonRoomId: number;

  histories: History[] = [
    {
      title: "History 1",
      contents: "Contetns of history 1 sdiuheuifsheusemperfaucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscsemper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suschduisheduh"
    },
    {
      title: "History 2",
      contents: "Contetns of history 1 sdiuheuifsheuhdrrrrrrrrrrrrrrsemper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscrrrrrrrrrrrrrrrrrruisheduh"
    },
    {
      title: "History 3",
      contents: "Contetns of history 1 sdiuheuifsheuhdrrrrrrrrrrrrrrsemper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscrrrrrrrrrrrrrrrrrruisheduh"
    },
    {
      title: "History 4",
      contents: "Contetns of history 1 sdiuheuifsheuhdrrrrrrrrrrrrrrsemper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscrrrrrrrrrrrrrrrrrruisheduh"
    },
    {
      title: "History 5",
      contents: "Contetns of history 1 sdiuheuifsheuhdrrrrrrrrrrrrrrsemper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscrrrrrrrrrrrrrrrrrruisheduh"
    }
  ];


  feedbacks: Feedback[] = [
    {
      name: "john",
      surname: "dee",
      contents: "ontetns of history 1 sdiuheuifsheusemperfaucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscse",
      rating: 4.5
    },
    {
      name: "joker",
      surname: "ll",
      contents: "ontetns of history 1 sdiuheuifsheusemperfaucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscse",
      rating: 3
    }
  ];


  description = "Aenean rhoncus semper faucibus. Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscipit elit ut, aliquam turpis. Aenean ornare varius augue nec scelerisque. Phasellus turpis leo, venenatis sit amet imperdiet sit amet, aliquam sit amet est. Ut finibus elit a libero semper, ac faucibus lectus luctus. Cras vestibulum nulla quis arcu faucibus, sed molestie ex iaculis. Phasellus facilisis ipsum magna, quis pellentesque erat auctor sit amet. Aenean blandit magna quis est elementum elementum. Aenean tincidunt justo eu erat maximus aliquet. Etiam laoreet velit nec turpis vulputate elementum. Nunc convallis, tortor quis ultricies fringilla, magna tellus fringilla enim, ut gravida justo purus et lacus."

  goBack() {
    this._location.back();
  }


  constructor(private _location: Location, private router: Router,
    private userService: UserService, private route: ActivatedRoute,
    private achievementService: AchievementService,
    private ratingService: RatingService, private chatService: ChatService,
              private blogService:BlogService) {}

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.loadCommonRoom(this.id, this.userService.currentUser.userId);
    this.loadUser(this.id);
    this.loadFollowers(this.id);
    this.loadLanguages(this.id);
    this.loadAchievements(this.id);
    this.loadRating(this.id);
  }

  loadUser(id: number) {
    this.loading=true;
    this.userService.get(id).subscribe( user =>
      this.user = user
    );

    this.loading = true;
    this.sub = this.userService.get(id).subscribe(
      async user => {
        this.loading = false;
        if (user) {
          this.user = user;
          await this.loadLastPost(id);
        }});
  }


  loadFollowers(id: number) {
    this.userService.getUserFollowers(id).subscribe( res => {
      this.followers = res;
      this.following = (this.followers.indexOf(this.userService.currentUser.userId) != -1)
    });
  }

  loadLanguages(id: number) {
    this.userService.getUserLanguages(id).subscribe(res =>
      this.languages = res.map(l => l.name)
    );
  }

  loadLastPost(id: number){
    this.blogService.getBlogPostsByUserId(0,1, id).subscribe(posts =>
      this.lastPost=new Blog_list_item(posts.pop(),
        this.user.firstName+" "+this.user.lastName,
        this.user.photo, this.id))
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

  isCurrentUser(){
    return this.userService.currentUser.userId==this.id
  }

}
