import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

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
}
