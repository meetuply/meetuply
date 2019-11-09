import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router'
import {UserService} from "../_services";
import {Location} from "@angular/common";
import {FeedbackService} from "../_services/feedback.service";
import {RatingService} from "../_services/rating.service";
import {Rating} from "../_models/rating";
import {Feedback} from "../_models/feedback";

@Component({
  selector: 'app-feedback-create-page',
  templateUrl: './feedback-create-page.component.html',
  styleUrls: ['./feedback-create-page.component.less']
})
export class FeedbackCreatePageComponent implements OnInit {

  rating: number;
  feedback: string;
  id: number;
  isActive:boolean;

  constructor(private _location: Location, private router: Router,
              private route: ActivatedRoute,
              private userService: UserService,
              private feedbackService: FeedbackService,
              private ratingService: RatingService) {
  }

  submit($event) {
    window.document.getElementById("rating-error").setAttribute("style", "display:none;");
    if (this.rating <= 0 || this.rating == null) {
      window.document.getElementById("rating-error").setAttribute("style", "display:block;");
    } else {
      let new_rating: Rating = {
        value: this.rating,
        date: new Date(Date.now()),
        ratedUser: this.id,
        ratedBy: this.userService.currentUser.userId
      };

      this.ratingService.updateRating(new_rating).subscribe(data => {
        if (data == null) {

          if (this.feedback.length > 0) {

            let new_feedback: Feedback = {
              feedbackId: 0,
              feedbackTo: this.id,
              feedbackBy: this.userService.currentUser.userId,
              feedbackContent: this.feedback,
              date: new Date(Date.now())
            };

            console.log(new_feedback);

            this.feedbackService.createFeedback(new_feedback).subscribe(data => {
              if (data == null) {
              }
            }, error => {
              console.log(error);
            });
          }
          this.router.navigate(['speakers/'+this.id]);
        }

      }, error => {
        console.log(error);
      });
    }
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.rating = 1;
    this.feedback = "";

    this.userService.get(this.id).subscribe(
      data => {
        this.isActive = (data && !data.deactivated)
        this.feedbackService.getWaitingFeedback(this.userService.currentUser.userId).subscribe(ids =>{
          this.isActive = this.isActive && ids.some(x => x == this.id);
        })
      }
    )


  }

  goBack() {
    this._location.back();
  }

}
