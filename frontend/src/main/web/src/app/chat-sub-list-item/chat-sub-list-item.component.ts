import { Component, OnInit, Input } from '@angular/core';
import { User } from '../_models'
import { Router } from '@angular/router'
import { ChatService } from '../_services/chat.service'
import { UserService } from '../_services/user.service'

@Component({
  selector: 'app-chat-sub-list-item',
  templateUrl: './chat-sub-list-item.component.html',
  styleUrls: ['./chat-sub-list-item.component.less']
})
export class ChatSubListItemComponent implements OnInit {

  @Input() user: User;

  commonRoomId: number;

  loadCommonRoom(id1: number, id2: number) {
    this.chatService.haveCommonRoom(id1, id2).subscribe(
      common => this.commonRoomId = common
    )
  }

  message() {

    if (this.user.userId != this.userService.currentUser.userId) {
      if (this.commonRoomId == -1) {

        this.chatService.createCommmonRoom(this.user.userId, this.userService.currentUser.userId).subscribe(
          room => this.router.navigateByUrl("/chats/" + room)
        )
      } else {
        this.router.navigateByUrl("/chats/" + this.commonRoomId)
      }
    }
  }


  constructor(private chatService: ChatService, private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.loadCommonRoom(this.user.userId, this.userService.currentUser.userId);
  }


}
