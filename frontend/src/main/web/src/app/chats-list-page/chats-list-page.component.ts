import { Component, OnInit } from '@angular/core';

import { UserService } from '../_services/user.service'
import { ChatThumbnail } from '../_models/chatThumbnail'
import { User } from '../_models';

@Component({
  selector: 'app-chats-list-page',
  templateUrl: './chats-list-page.component.html',
  styleUrls: ['./chats-list-page.component.less']
})
export class ChatsListPageComponent implements OnInit {




  rooms: ChatThumbnail[] = [];

  followings: User[] = [];

  show = false;

  constructor(private userService: UserService) { }


  loadChatRooms(userId: number) {

    this.userService.getRoomsThumbnails(userId).subscribe(
      res => {
        this.rooms = res;
        //console.log(res);
      }
    )
  }


  isPresentInThumbnail(user: User) {

    let ch1 = this.rooms.filter(thumbnail => thumbnail.message === null);
    if (ch1.length >= 1) return false;

    let rms = this.rooms.filter(thumbnail => thumbnail.user.userId == user.userId)
    if (rms.length == 0) {
      return false;
    } else {
      return true;
    }
  }




  loadFollowings(userId: number) {


    this.userService.getUserFollowingsList(userId).subscribe(
      users => {
        this.followings = users.filter(user => !this.isPresentInThumbnail(user));
        //console.log(users)
      }
    )
  }

  ngOnInit() {
    this.loadChatRooms(this.userService.currentUser.userId);
    this.loadFollowings(this.userService.currentUser.userId);
  }

  write_message() {
    this.show = !this.show;
  }

}
