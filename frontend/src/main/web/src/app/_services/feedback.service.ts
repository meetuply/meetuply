import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Feedback} from "../_models/feedback";
import {BlogPost} from "../_models";

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  private feedbackApiUrl = `${environment.apiUrl}/api/feedbacks/`;

  constructor(private http: HttpClient) {
  }

  getFeedbackByTo(idby: number, idto: number): Observable<Feedback>{
    return this.http.get<Feedback>(this.feedbackApiUrl + `${idby}/${idto}`);
  }

  getFeedbackTo(id:number): Observable<Feedback[]>{
    return this.http.get<Feedback[]>(this.feedbackApiUrl + `user/${id}`);
  }

  getWaitingFeedback(id:number): Observable<number[]> {
    return this.http.get<number[]>(this.feedbackApiUrl + `${id}/feedback-waiting`);
  }

  createFeedback(feedback: Feedback): Observable<{}> {
    return this.http.post(this.feedbackApiUrl + `${feedback.feedbackTo}`, feedback);
  }
}
