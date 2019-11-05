import { Component, OnInit, Input } from '@angular/core';
import { User } from '../_models';
import { Message } from '../_models';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-chats-list-item',
  templateUrl: './chats-list-item.component.html',
  styleUrls: ['./chats-list-item.component.less']
})
export class ChatsListItemComponent implements OnInit {

  @Input() user: User;
  @Input() message: Message;
  @Input() roomId: number;

  constructor(private userService: UserService) { }

  textStyle() {
    return {
      "color": (this.message.from != this.userService.currentUser.userId ? '#2A8BF2' : 'black')
    }
  }

  time() {
    var d = new Date(this.message.date_time);
    var minutes = ("00" + d.getMinutes()).slice(-2)
    return d.getHours() + ":" + minutes
  }

  ngOnInit() {

  }

  link() {
    return "/chats/" + this.roomId;
  }

}
