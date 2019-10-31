import {Meetup} from "./meetup";

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


	constructor(meetup: Meetup, author: string, authorPhoto: string,
              rate: number){
	  this.meetup = meetup;
	  this.joined = true;
	  this.author = author;
	  this.authorPhoto = authorPhoto;
	  this.rate = rate;
  }
}
