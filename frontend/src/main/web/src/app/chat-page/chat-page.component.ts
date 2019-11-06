import { Component, OnInit, HostListener } from '@angular/core';
import * as Stomp from 'stompjs'
import * as SockJS from 'sockjs-client'
import { ActivatedRoute, Router } from "@angular/router";
import { ChatService } from '../_services/chat.service'
import { UserService } from '../_services'
import {Message, User} from '../_models'
import { Location } from '@angular/common'
import { environment } from "../../environments/environment";

@Component({
  selector: 'app-chat-page',
  templateUrl: './chat-page.component.html',
  styleUrls: ['./chat-page.component.less']
})
export class ChatPageComponent implements OnInit {

  roomId: number;
  stompClient: Stomp.stompClient;
  messages: Message[] = [];
  message: string = "";

  other: User;

  constructor(private chatService: ChatService, private userService: UserService,
    private route: ActivatedRoute, private _location: Location, private router: Router) {

  }

  loadInitMessages(roomId: number) {
    this.chatService.getRoomMessages(roomId).subscribe(
      messages => {
        this.messages = messages;

      }
    );
  }


  loadMember(roomId: number) {

    this.chatService.getRoomMembers(roomId).subscribe(
      members => {

        const index = members.indexOf(this.userService.currentUser.userId, 0);
        if (index > -1) {
          members.splice(index, 1);


          if (members.length <= 0) {
            this.router.navigateByUrl('/chats');
          }
          else {

            let otherId = members[0]
            this.userService.get(otherId).subscribe(
              user => this.other = user
            )

          }


        }
        else {
          this.router.navigateByUrl('/chats');
        }

      }

    );

  }

  connect() {


    var socket = new SockJS(`${environment.apiUrl}/meetuply`);


    this.stompClient = Stomp.over(socket);


    this.stompClient.connect({}, frame => {

      console.log('Connected: ' + frame);

      this.subscribe();


    });
  }


  subscribe() {
    this.stompClient.subscribe('/chat/session_messages/' + this.roomId, greeting => {
      var msg: Message = JSON.parse(greeting.body);
      this.messages.unshift(msg);
    });
  }


  goBack() {
    this._location.back();

  }

  ngOnInit() {

    this.height = window.innerHeight;
    this.roomId = this.route.snapshot.params['id'];

    this.loadMember(this.roomId);
    
    this.connect();

    this.loadInitMessages(this.roomId);
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.height = window.innerHeight;
  }

  height: number;

  setStyle() {
    return {
      'height': this.height + "px"
    }
  }



  onKeydown(event) {
    if (event.key === "Enter") {
      this.sendMessage();
    }
  }

  sendMessage() {

    var msg: Message = {
      uid: 0,
      content: this.message,
      date_time: new Date(Date.now()),
      from: this.userService.currentUser.userId,
      to_user_id: 1,
      to_room_id: this.roomId
    };

    this.stompClient.send("/app/in", {}, JSON.stringify(msg));
    this.message = "";

  }
}
