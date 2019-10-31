import { Injectable } from "@angular/core";
import {Topic} from "../_models";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { Observable, throwError } from "rxjs";
import { environment } from "../../environments/environment";


@Injectable({ providedIn: 'root' })
export class TopicService {


  private topicApiUrl = `${environment.apiUrl}/api/topics/`;

  constructor(private http: HttpClient) { }

  /** GET topics from the server */
  getAll():Observable<Topic[]> {
    return this.http.get<Topic[]>(this.topicApiUrl);
  }

  /** POST: add a new topic to the server */
  createTopic(topic: Topic): Observable<{}> {
    return this.http.post(this.topicApiUrl, topic);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    return throwError(
      'Something bad happened; please try again later.');
  };

}
