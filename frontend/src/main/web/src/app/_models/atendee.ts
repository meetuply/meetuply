import {RatingService} from "../_services/rating.service";

export class Atendee {
  id: number;
	name: string;
	surname: string;
	rating: number;
	photo: string;

	constructor(id:number, name: string, surname: string, photo: string,
              private ratingService: RatingService){
	  this.id = id;
	  this.name = name;
	  this.surname = surname;
	  this.photo = photo;
	  this.ratingService.getUserRatingAvg(this.id).subscribe(
	    avgRating => {
	      this.rating = avgRating;
      }
    )
  }
}
