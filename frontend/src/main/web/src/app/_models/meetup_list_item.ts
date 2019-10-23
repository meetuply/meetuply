import {Meetup} from "./meetup";
import {UserService} from "../_services";
import {RatingService} from "../_services/rating.service";

//todo get author
export class Meetup_list_item {
  meetup: Meetup;
	author: string;
	authorPhoto: string;
	joined: boolean;
	rate: number;
	get date() {return this.meetup.meetupStartDateTime}
	get description(){ return this.meetup.meetupDescription}
	get title(){return this.meetup.meetupTitle}
	get place() {return this.meetup.meetupPlace}
	get maxAttendees() {return this.meetup.meetupMaxAttendees}
	get registeredAttendees() {return this.meetup.meetupRegisteredAttendees}
	get id() {return this.meetup.meetupId}

	//todo add rating, joined
	constructor(meetup: Meetup, private userService: UserService,
              private ratingService: RatingService){
	  this.meetup = meetup;
	  this.joined = true;
	  this.userService.get(meetup.speakerId).subscribe(
	    user => {
	      this.authorPhoto = user['photo'];
	      this.author = user['firstName'] + " " + user['lastName'];
      }
    );
    this.ratingService.getUserRatingAvg(meetup.speakerId).subscribe(
      avgRating => {
        this.rate = avgRating;
      }
    )
  }
}
