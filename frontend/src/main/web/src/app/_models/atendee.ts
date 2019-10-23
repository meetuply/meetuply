import {RatingService} from "../_services/rating.service";

export class Atendee {
  id: number;
	name: string;
	surname: string;
	rating: number;
	photo: string;

	constructor(id:number, name: string, surname: string, photo: string,
              rating:number){
	  this.id = id;
	  this.name = name;
	  this.surname = surname;
	  this.photo = photo;
	  this.rating = rating;
  }
}
