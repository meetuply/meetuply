import {Component, Input, OnInit} from '@angular/core';
import {MeetupService} from "../_services/meetup.service";
import {Meetup} from "../_models/meetup";
import {UserService} from "../_services";

@Component({
  selector: 'app-meetup-list-item',
  templateUrl: './meetup-list-item.component.html',
  styleUrls: ['./meetup-list-item.component.less']
})

export class MeetupListItemComponent implements OnInit {
  @Input() meetup: Meetup;
  @Input() time: string;
  @Input() date: string;
  @Input() rate: number;
  @Input() author: string;
  @Input() joined: boolean;
  @Input() description: string;
  @Input() title: string;
  @Input() place: string;
  @Input() maxAttendees: number;
  @Input() registeredAttendees: number;
  @Input() id: number;

  constructor() {
  }

  ngOnInit() {
  }

  joinType() {
    return (this.joined == true ? '2' : (this.meetup.meetupMaxAttendees == this.meetup.meetupRegisteredAttendees ? "3" : "1"));
  }

  joinText() {
    return (this.joined == true ? 'Leave' : (this.meetup.meetupMaxAttendees == this.meetup.meetupRegisteredAttendees ? "Full" : "Join"));
  }
}
