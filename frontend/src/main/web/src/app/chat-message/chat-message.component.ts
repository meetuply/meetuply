import { Component, OnInit, Input } from '@angular/core';
import { Message } from '../_models'
import { User } from '../_models'
import { UserService } from '../_services/user.service'

@Component({
  selector: 'app-chat-message',
  templateUrl: './chat-message.component.html',
  styleUrls: ['./chat-message.component.less']
})
export class ChatMessageComponent implements OnInit {


  @Input() msg: Message;

  foreign: boolean;


  constructor(private userService: UserService) { }

  ngOnInit() {
    this.foreign = this.userService.currentUser.userId != this.msg.from;
  }

  time() {
    var d = new Date(this.msg.date_time);
    var minutes = ("00" + d.getMinutes()).slice(-2)
    return d.getHours() + ":" + minutes
  }

}
