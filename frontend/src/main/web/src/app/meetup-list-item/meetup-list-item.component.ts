import { Component, OnInit, Input } from '@angular/core';
import {MeetupService} from "../_services/meetup.service";
import {Meetup} from "../_models/meetup";

@Component({
  selector: 'app-meetup-list-item',
  templateUrl: './meetup-list-item.component.html',
  styleUrls: ['./meetup-list-item.component.less']
})
export class MeetupListItemComponent implements OnInit {

  @Input() meetup: Meetup;
  @Input() title: string;
  @Input() author: string;
  @Input() location: string;
  @Input() rate: number;

  @Input() description: string;
  @Input() time: string;
  @Input() date: string;
  @Input() maxMembers: number;
  @Input() members: number;
  @Input() joined: boolean;
  @Input() id: number;

  error;


  constructor(private meetupService: MeetupService) { }

  ngOnInit() {
    this.joined = false // todo remove
  }


  joinType() {
    return (this.joined == true ? '2' : (this.maxMembers == this.members ? "3" : "1"));
  }

  joinText() {
    return (this.joined == true ? 'Leave' : (this.maxMembers == this.members ? "Full": "Join" ));
  }

  joinButtonClicked(event){
    if (this.joined)
      this.meetupService.leaveMeetup(this.meetup.meetupId).subscribe(
        data => {
          this.joined = false;
          this.meetup.meetupRegisteredAttendees--
        },
        error => {
          this.error = error;
          console.log(error)

        }
      );
    else if (this.meetup.meetupMaxAttendees != this.meetup.meetupRegisteredAttendees)
      this.meetupService.joinMeetup(this.meetup.meetupId).subscribe(
        data => {
          this.joined = true;
          this.meetup.meetupRegisteredAttendees++
        },
        error => {
          this.error = error;
          console.log(error)
        }
      );
  }

}
