import {Meetup} from "./meetup";
import {UserService} from "../_services";

//todo get author
export class Meetup_list_item {
  meetup: Meetup;
	author: string;
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
	constructor(meetup: Meetup, private userService: UserService){
	  this.meetup = meetup;
	  this.joined = true;
	  this.rate = 4;
	  this.userService.get(meetup.speakerId).subscribe(
	    user => {
	      this.author = user['firstName'] + " " + user['lastName'];
      }
    )
  }
}
