import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Rating} from "../_models/rating";


@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private ratingApiUrl = `${environment.apiUrl}/api/ratings/`;

  constructor(private http: HttpClient) {
  }

  getUserRatingAvg(id: number) {
    return this.http.get<number>(this.ratingApiUrl + `${id}` + "/avg");
  }

  getRatingByTo(idby: number, idto: number): Observable<{}>{
    return this.http.get(this.ratingApiUrl + `${idby}` +"/"+`${idto}`);
  }

  createRating(rating: Rating): Observable<{}> {
    return this.http.post<Rating>(this.ratingApiUrl + `${rating.ratedUser}`, rating);
  }

  updateRating(rating: Rating): Observable<{}> {
    return this.http.put<Rating>(this.ratingApiUrl + `${rating.ratedUser}`, rating);
  }
}
