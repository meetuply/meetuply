import {Component, OnInit} from '@angular/core';
import {Meetup} from "../_models/meetup";
import {ActivatedRoute} from "@angular/router";
import {MeetupService} from "../_services/meetup.service";
import {Subscription} from "rxjs";
import {UserService} from "../_services";
import {RatingService} from "../_services/rating.service";
import {Atendee} from "../_models/atendee";
import {StateService} from "../_services/state.service";
import { Location } from '@angular/common';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../confirm-dialog/confirm-dialog.component";
import {RescheduleDialogComponent} from "../reschedule-dialog/reschedule-dialog.component";
import {log} from "util";

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
  hasAccess = false;
  state: string;

  constructor(
    private meetupService: MeetupService,
    private userService: UserService,
    private ratingService: RatingService,
    private route: ActivatedRoute,
    private stateService: StateService,
    private location: Location,
    private dialog: MatDialog) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.loadMeetup(this.id);
  }


  loadMeetup(id: number) {
    this.loading = true;
    this.sub = this.meetupService.get(id).subscribe(
      data => {
        this.meetup = data;
        this.loading = false;
        this.meetup = data;
        this.getAuthorInfo(data['speakerId']);
        this.getAttendees();
        this.hasAccess = this.meetup.speakerId == this.userService.currentUser.userId || this.userService.currentUser.role.roleName == 'admin';
        this.state = this.stateService.states[this.meetup.stateId];
        console.log(this.state);
      },
      error => {
        // this.alertService.error(error);
        this.loading = false;
      }
    );
  }

  getAttendees() {
    this.loading = true;
    this.meetupService.getAttendees(this.id).subscribe(
      async data => {
        this.loading = false;
        this.attendees = await Promise.all(data.map(async user => {
          let rating = 0;
          await this.ratingService.getUserRatingAvg(user.userId).toPromise(
          ).then(data => {
            rating = data
          });
          return new Atendee(user.userId, user.firstName,
            user.lastName, user.photo, rating)
        }));
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

  getAuthorInfo(id: number) {
    this.loading = true;
    this.meetupService.getSpeaker(this.meetup.speakerId).subscribe(
      data => {
        this.loading = false;
        this.author = data['firstName'] + " " + data['lastName'];
        this.authorPhoto = data['photo']
      }
    );
    this.ratingService.getUserRatingAvg(this.meetup.speakerId).subscribe(
      avgRating => {
        this.rate = avgRating;
      }
    )
  }

  joinButtonClicked(event) {
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
          ).then(data => {
            rating = data
          });
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
    this.openConfirmDialog("Cancel meetup", "Are you sure that you want to cancel meetup?")
      .subscribe(data => {
        if (data) {
          this.meetupService.cancell(this.id).subscribe(
            data => this.loadMeetup(this.id),
            error => this.error = error
          )
        }
      })
  }

  canTerminate() {
    return this.state == "In progress"
  }

  terminateMeetup(event) {
    this.openConfirmDialog("Terminate meetup", "Are you sure that you want to terminate meetup?")
      .subscribe(data => {
        if (data) {
          this.meetupService.terminate(this.id).subscribe(
            data => this.loadMeetup(this.id),
            error => this.error = error
          )
        }
      })

  }

  canReschedule() {
    return this.state == "Terminated"
  }

  reschedule(event) {
    this.openRescheduleDialog('Reschedule meetup', 'Choose new date for meetup')
      .subscribe(data => {
          data.meetupId = this.id;
          this.meetupService.reschedule(data).subscribe(
            data => this.loadMeetup(this.id),
            error => this.error = error)
        },
        error => this.error = error
      )
  }

  openConfirmDialog(title, description) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '500px';
    dialogConfig.data = {
      title: title,
      description: description
    };
    const dialogRef = this.dialog.open(ConfirmDialogComponent, dialogConfig);

    return dialogRef.afterClosed();
  }

  openRescheduleDialog(title, description) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '500px';
    dialogConfig.data = {
      title: title,
      description: description
    };
    const dialogRef = this.dialog.open(RescheduleDialogComponent, dialogConfig);
    return dialogRef.afterClosed();
  }

  joinType() {
    return (this.joined == true ? '2' : (this.meetup.meetupMaxAttendees == this.attendees.length ? "3" : "1"));
  }

  joinText() {
    return (this.joined ? 'Leave' : (this.meetup.meetupMaxAttendees == this.attendees.length ? "Full" : "Join"));
  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe();
  }

  goBack(){
  this.location.back();
  }
}
