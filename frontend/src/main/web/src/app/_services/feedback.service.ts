import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Feedback} from "../_models/feedback";

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  private feedbackApiUrl = `${environment.apiUrl}/api/feedbacks/`;

  constructor(private http: HttpClient) {
  }

  getFeedbackByTo(idby: number, idto: number): Observable<{}>{
    return this.http.get(this.feedbackApiUrl + `${idby}` +"/"+`${idto}`);
  }

  getWaitingFeedback(id:number): Observable<number[]> {
    return this.http.get<number[]>(this.feedbackApiUrl + `${id}/feedback-waiting`);
  }

  createFeedback(feedback: Feedback): Observable<{}> {
    return this.http.put(this.feedbackApiUrl + `${feedback.feedbackTo}`, feedback);
  }
}
