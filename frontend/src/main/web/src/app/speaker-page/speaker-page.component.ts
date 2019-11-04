import { Component, OnInit } from '@angular/core';
import { History } from '../history'
import { Feedback } from "../feedback"
import { Location } from '@angular/common';
import { HttpClient } from "@angular/common/http";
import { ActivatedRoute, Router } from "@angular/router";

import { User } from '../_models'
import { UserService } from '../_services/user.service'
import { ChatService } from '../_services/chat.service'

@Component({
  selector: 'app-speaker-page',
  templateUrl: './speaker-page.component.html',
  styleUrls: ['./speaker-page.component.css', '../fonts.css']
})
export class SpeakerPageComponent implements OnInit {


  id: number;
  user: User;
  followers: number;
  languages: string[];
  rate: 3;


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

  constructor(private _location: Location, private router: Router, private userService: UserService, private chatService: ChatService, private route: ActivatedRoute) {
  }

  loadUser(id: number) {
    this.userService.get(id).subscribe(user =>
      this.user = user
    );
  }

  loadFollowers(id: number) {
    this.userService.getUserFollowers(id).subscribe(res =>
      this.followers = res.length
    );
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



  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.loadCommonRoom(this.id, this.userService.currentUser.userId);
    this.loadUser(this.id);
    this.loadFollowers(this.id);
    this.loadLanguages(this.id);

  }

}
