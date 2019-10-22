import {Meetup} from "./meetup";
import {UserService} from "../_services";
import {User} from "./user";

//todo get author
export class Meetup_list_item {
  meetup: Meetup;
	author: string;
	time: string;
	date: string;
	joined: boolean;
	rate: number;
	get description(){ return this.meetup.meetupDescription}
	get title(){return this.meetup.meetupTitle}
	get place() {return this.meetup.meetupPlace}
	get maxAttendees() {return this.meetup.meetupMaxAttendees}
	get registeredAttendees() {return this.meetup.meetupRegisteredAttendees}
	get id() {return this.meetup.meetupId}

	constructor(meetup: Meetup,  time: string, date: string, joined:boolean, rate:number, private userService: UserService){
	  this.meetup = meetup;
	  this.time = time;
	  this.date = date;
	  this.joined = joined;
	  this.rate = rate;
	  userService.get(this.id).subscribe(
	    user => {
	      this.author = user['firstName'] + " " + user['lastName'];
      }
    )
  }
}
