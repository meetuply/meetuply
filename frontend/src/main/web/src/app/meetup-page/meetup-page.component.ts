import {Component, OnInit} from '@angular/core';
import {Meetup} from "../_models/meetup";
import {ActivatedRoute} from "@angular/router";
import {MeetupService} from "../_services/meetup.service";
import {Subscription} from "rxjs";
import {UserService} from "../_services";
import {RatingService} from "../_services/rating.service";
import {Atendee} from "../_models/atendee";
import {StateService} from "../_services/state.service";

@Component({
  selector: 'app-meetup-page',
  templateUrl: './meetup-page.component.html',
  styleUrls: ['./meetup-page.component.less']
})
export class MeetupPageComponent implements OnInit {

  meetup: Meetup = new Meetup();
  loading = false;
  private sub: Subscription;
  id: number;
  author: string;
  authorPhoto: string;
  rate = 4;
  joined = false;
  error = null;
  attendees: Atendee[] = [];
  isMy = false;
  state: string;

  constructor(
    private meetupService: MeetupService,
    private userService: UserService,
    private ratingService: RatingService,
    private route: ActivatedRoute,
    private stateService: StateService) {
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
        this.isMy = this.meetup.speakerId == this.userService.currentUser.userId;
        this.state = this.stateService.states[this.meetup.stateId];
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
      async data => {
        this.loading = false;
        this.attendees = await Promise.all(data.map(async user =>{
          let rating = 0;
          await this.ratingService.getUserRatingAvg(user.userId).toPromise(
          ).then(data => {rating = data});
          return new Atendee(user.userId, user.firstName,
          user.lastName, user.photo, rating)}));
        data.forEach(a => {
          if (a.userId == this.userService.currentUser.userId) {
            this.joined = true;
            return;
          }
        }
      )
      }
    )
  }

  getAuthorInfo(id: number){
    this.loading = true;
    this.meetupService.getSpeaker(this.meetup.speakerId).subscribe(
      data => {
        this.loading = false;
        this.author = data['firstName']+ " " + data['lastName'];
        this.authorPhoto = data['photo']
      }
    );
    this.ratingService.getUserRatingAvg(this.meetup.speakerId).subscribe(
      avgRating => {
        this.rate = avgRating;
      }
    )
  }

  joinButtonClicked(event){
    if (this.joined)
      this.meetupService.leaveMeetup(this.meetup.meetupId).subscribe(
        data => {
          this.joined = false;
          this.attendees = this.attendees.filter(e => e.id != this.userService.currentUser.userId);
        },
        error => {
          this.error = error;
          console.log(error)

        }
      );
    else if (this.meetup.meetupMaxAttendees != this.attendees.length)
      this.meetupService.joinMeetup(this.meetup.meetupId).subscribe(
        async data => {
          this.joined = true;
          let currentUser = this.userService.currentUser;
          let rating = 0;
          await this.ratingService.getUserRatingAvg(currentUser.userId).toPromise(
          ).then(data => {rating = data});
          let addAttendee = new Atendee(currentUser.userId, currentUser.firstName,
            currentUser.lastName, currentUser.photo, rating);
          this.attendees.unshift(addAttendee);
        },
        error => {
          this.error = error;
          console.log(error)
        }
      );
  }

  canCancell() {
    return this.state == "Scheduled" || this.state == "Booked" || this.state == "Terminated"
  }

  cancellMeetup(event) {
    if(confirm("Are you sure that you want to cancel meetup?")) {
      this.meetupService.cancell(this.id).subscribe(
        data => this.loadMeetup(this.id),
        error => this.error = error
      )
    }
  }

  canTerminate() {
    return this.state == "In progress"
  }

  terminateMeetup(event) {
    if(confirm("Are you sure that you want to terminate meetup?")) {
      this.meetupService.terminate(this.id).subscribe(
        data => this.loadMeetup(this.id),
        error => this.error = error
      )
    }
  }

  canReschedule() {
    return this.state == "Terminated"
  }

  reschedule(event) {

  }

  joinType() {
    return (this.joined == true ? '2' : (this.meetup.meetupMaxAttendees == this.attendees.length ? "3" : "1"));
  }

  joinText() {
    return (this.joined ? 'Leave' : (this.meetup.meetupMaxAttendees == this.attendees.length ? "Full" : "Join"));
  }

  ngOnDestroy(){
    if (this.sub) this.sub.unsubscribe();
  }
}
