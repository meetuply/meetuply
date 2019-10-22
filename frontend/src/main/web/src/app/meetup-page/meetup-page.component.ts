import {Component, OnInit} from '@angular/core';
import {Meetup} from "../_models/meetup";
import {ActivatedRoute} from "@angular/router";
import {MeetupService} from "../_services/meetup.service";
import {User} from "../_models";
import {Subscription} from "rxjs";
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
  private sub: Subscription;
  id: number;
  author: string;
  rate = 4;
  joined = true;
  error = null;
  attendees: User[];

  constructor(
    private meetupService: MeetupService,
    private userService: UserService,
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
        this.getAuthorName(data['speakerId']);
        this.getAttendees();
      },
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
        this.attendees = data;
      }
    )
  }

  getAuthorName(id: number){
    this.loading = true;
    this.userService.get(id).subscribe(
      data => {
        this.loading = false;
        this.author = data['firstName']+" " + data['lastName']},
      error1 => {
        this.loading = false;
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
