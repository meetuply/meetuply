import {Component, OnInit} from '@angular/core';
import {Meetup} from "../_models/meetup";
import {ActivatedRoute, Router} from "@angular/router";
import {MeetupService} from "../_services/meetup.service";
import {User} from "../_models";
import {UserService} from "../_services";

@Component({
  selector: 'app-meetup-page',
  templateUrl: './meetup-page.component.html',
  styleUrls: ['./meetup-page.component.less']
})
export class MeetupPageComponent implements OnInit {

  //todo ratind
  meetup: Meetup = new Meetup();
  loading = false;
  private sub: any;
  id: number;
  author: string;
  date: Date;
  time: string;
  rate = 4;
  joined = true;
  error = null;
  atendees: User[] = [];

  constructor(
    private meetupService: MeetupService,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.loadMeetup(this.id);

  }

  loadMeetup(id:number) {
    this.loading = true;
    this.sub = this.meetupService.get(id).subscribe(
      data => {
        this.loading = false;
        this.meetup = data;
        this.date = data.meetupStartDateTime;
        this.getMeetupSpeakerName();
        this.getAttendees();},
      error => {
        // this.alertService.error(error);
        this.loading = false;
      }
    );
  }

  getAttendees(){
    this.loading = true;
    this.meetupService.getAttendees(this.id).subscribe(
      data => {
        this.loading = false;
        this.atendees = data;
      }
    )
  }

  getMeetupSpeakerName(){
    this.meetupService.getSpeaker(this.meetup.speakerId).subscribe(
      data => {
        this.author = data['firstName'] +" "+ data['lastName'];
      }
    );
  }

  joinButtonClicked(event){
    if (this.joined)
      this.meetupService.leaveMeetup(this.meetup.meetupId).subscribe(
        data => {
          this.joined = false;
          this.atendees = this.atendees.filter(e => e.userId != this.userService.currentUser.userId);
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
          this.atendees.unshift(this.userService.currentUser);
        },
        error => {
          this.error = error;
          console.log(error)
        }
      );
  }

  joinType() {
    return (this.joined == true ? '2' : (this.meetup.meetupMaxAttendees == this.meetup.meetupRegisteredAttendees ? "3" : "1"));
  }

  joinText() {
    return (this.joined ? 'Leave' : (this.meetup.meetupMinAttendees == this.meetup.meetupRegisteredAttendees ? "Join" : "Full"));
  }

  ngOnDestroy(){
    if (this.sub) this.sub.unsubscribe;
  }

  goBack() {
  }
}
