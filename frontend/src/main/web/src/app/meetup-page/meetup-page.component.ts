import {Component, OnInit} from '@angular/core';
import {Meetup} from "../_models/meetup";
import {ActivatedRoute, Router} from "@angular/router";
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
  authorPhoto: string;
  rate = 4;
  joined = true;
  error = null;
  attendees: User[] = [];

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
        console.log(data);
        this.meetup = data;
        this.loading = false;
        this.meetup = data;
        this.getAuthorInfo(data['speakerId']);
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

  getAuthorInfo(id: number){
    this.loading = true;
    this.meetupService.getSpeaker(this.meetup.speakerId).subscribe(
      data => {
        this.loading = false;
        this.author = data['firstName']+" " + data['lastName'];
        this.authorPhoto = data['photo']},
      error1 => {
        this.loading = false;
      }
    );
  }

  joinButtonClicked(event){
    if (this.joined)
      this.meetupService.leaveMeetup(this.meetup.meetupId).subscribe(
        data => {
          this.joined = false;
          this.attendees = this.attendees.filter(e => e.userId != this.userService.currentUser.userId);
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
          this.attendees.unshift(this.userService.currentUser);
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
