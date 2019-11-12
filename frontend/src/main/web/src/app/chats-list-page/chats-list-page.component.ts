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
        //console.log(this.rooms);
        
        this.loadFollowings(this.userService.currentUser.userId);
       
      }
    )
  }


  loadFollowings(userId: number) {

    var userIds = this.rooms.filter( room => room.message != null ).map( room => room.user.userId);
    
    this.userService.getUserFollowingsList(userId).subscribe(
      users => {
        this.followings = users.filter(user => !userIds.includes(user.userId));
       
      })

    
  }

  ngOnInit() {
    this.loadChatRooms(this.userService.currentUser.userId);
    
  }

  write_message() {
    this.show = !this.show;
  }

}
