import {Component, Input, OnInit} from '@angular/core';
import {MeetupService} from "../_services/meetup.service";
import {UserService} from "../_services";

@Component({
  selector: 'app-meetup-list-item',
  templateUrl: './meetup-list-item.component.html',
  styleUrls: ['./meetup-list-item.component.less']
})


export class MeetupListItemComponent implements OnInit {
  @Input() meetupListItem;
  @Input() isMy: boolean;
  error;
  isAttendeLoaded = false;

  constructor(private meetupService: MeetupService,
              private userService: UserService
  ) { }

  ngOnInit() {
    this.meetupService.isAttendee(this.meetupListItem.meetupId, this.userService.currentUser.userId).subscribe(
      data => {
        this.meetupListItem.joined = data;
        this.isAttendeLoaded = true
      },
      error => this.error = error
    );
  }

  joinType() {
    return (this.meetupListItem.joined == true ? '2' : (this.meetupListItem.meetupMaxAttendees ==
    this.meetupListItem.meetupRegisteredAttendees ? "3" : "1"));
  }

  joinText() {
    return (this.meetupListItem.joined == true ? 'Leave' : (this.meetupListItem.meetupMaxAttendees ==
    this.meetupListItem.meetupRegisteredAttendees ? "Full" : "Join"));
  }

  joinButtonClicked(event){
    if (this.meetupListItem.joined)
      this.meetupService.leaveMeetup(this.meetupListItem.meetupId).subscribe(
        data => {
          this.meetupListItem.joined = false;
          this.meetupListItem.meetupRegisteredAttendees--
        },
        error => {
          this.error = error;

        }
      );
    else if (this.meetupListItem.meetupMaxAttendees != this.meetupListItem.meetupRegisteredAttendees)
      this.meetupService.joinMeetup(this.meetupListItem.meetupId).subscribe(
        data => {
          this.meetupListItem.joined = true;
          this.meetupListItem.meetupRegisteredAttendees++
        },
        error => {
          this.error = error;
        }
      );
  }
}
