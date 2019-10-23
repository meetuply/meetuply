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
  @Input() date: Date;
  @Input() rate: number;
  @Input() author: string;
  @Input() authorPhoto: string;
  @Input() joined: boolean;
  @Input() description: string;
  @Input() title: string;
  @Input() place: string;
  @Input() maxAttendees: number;
  @Input() registeredAttendees: number;
  @Input() uid: number;

  error;

  constructor(private meetupService: MeetupService) { }

  ngOnInit() {
//     this.joined = false
  }

  joinType() {
    return (this.joined == true ? '2' : (this.maxAttendees == this.registeredAttendees ? "3" : "1"));
  }

  joinText() {
    return (this.joined == true ? 'Leave' : (this.maxAttendees == this.registeredAttendees ? "Full" : "Join"));
  }

  joinButtonClicked(event){
    if (this.joined)
      this.meetupService.leaveMeetup(this.uid).subscribe(
        data => {
          this.joined = false;
          this.registeredAttendees--
        },
        error => {
          this.error = error;
          console.log(error)

        }
      );
    else if (this.maxAttendees != this.registeredAttendees)
      this.meetupService.joinMeetup(this.uid).subscribe(
        data => {
          this.joined = true;
          this.registeredAttendees++
        },
        error => {
          this.error = error;
          console.log(error)
        }
      );
  }
}
