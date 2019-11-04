import { Component, OnInit } from '@angular/core';

import { UserService } from '../_services/user.service'
import { ChatThumbnail } from '../_models/chatThumbnail'

@Component({
  selector: 'app-chats-list-page',
  templateUrl: './chats-list-page.component.html',
  styleUrls: ['./chats-list-page.component.less']
})
export class ChatsListPageComponent implements OnInit {




  rooms: ChatThumbnail[] = []

  constructor(private userService: UserService) { }


  loadChatRooms(userId: number) {

    this.userService.getRoomsThumbnails(userId).subscribe(
      res => this.rooms = res
    )
  }

  ngOnInit() {
    this.loadChatRooms(this.userService.currentUser.userId);

  }

  click() {
    
  }

}
