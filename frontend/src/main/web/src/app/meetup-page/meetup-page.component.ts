import {Component, OnInit} from '@angular/core';
import {Atendee} from '../_models/atendee'
import {Meetup} from "../_models/meetup";
import {ActivatedRoute, Router} from "@angular/router";
import {MeetupPageService} from "../_services/meetup-page.service";
import {User} from "../_models";

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
  date: string;
  time: string;
  rate = 4;
  joined = true;
  error = null;
  atendees: User[];

  constructor(
    private meetupPageService: MeetupPageService,
    private router: Router,
    private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.loadMeetup(this.id);

  }

  loadMeetup(id:number) {
    this.loading = true;
    this.sub = this.meetupPageService.get(id).subscribe(
      data => {
        this.loading = false;
        this.meetup = data;
        this.date = data.meetupStartDateTime.substring(0, 10);
        this.time = data.meetupStartDateTime.substring(11, 16);
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
    this.meetupPageService.getAttendees(this.id).subscribe(
      data => {
        this.loading = false;
        this.atendees = data;
      }
    )
  }

  getMeetupSpeakerName(){
    this.meetupPageService.getSpeaker(this.meetup.speakerId).subscribe(
      data => {
        this.author = data['firstName'] +" "+ data['lastName'];
      }
    );
  }

  joinType() {
    return (this.joined == true ? '2' : (this.meetup.meetupMaxAttendees == this.meetup.meetupRegisteredAttendees ? "3" : "1"));
  }

  joinText() {
    return (this.joined == true ? 'Leave' : (this.meetup.meetupMinAttendees == this.meetup.meetupRegisteredAttendees ? "Full" : "Join"));
  }

  ngOnDestroy(){
    if (this.sub) this.sub.unsubscribe;
  }

  goBack() {
  }
}
