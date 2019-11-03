import { Injectable } from "@angular/core";
import {Topic} from "../_models";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError, of } from "rxjs";
import { environment } from "../../environments/environment";


@Injectable({ providedIn: 'root' })
export class TopicService {


  private topicApiUrl = `${environment.apiUrl}/api/topics/`;

  constructor(private http: HttpClient) { }

  /** GET topics from the server */
  getAll():Observable<Topic[]> {
    return this.http.get<Topic[]>(this.topicApiUrl);
  }

  /** GET topic by id. Will 404 if id not found */
  getTopic(id: number): Observable<Topic> {
    return this.http.get<Topic>(this.topicApiUrl + `${id}`);
  }

  /** PUT: update the topic on the server */
  updateTopic (topic: Topic): Observable<Topic> {
    return this.http.put<Topic>(this.topicApiUrl + `${topic.topicId}`, topic);
  }

  /** POST: add a new topic to the server */
  createTopic(topic: Topic): Observable<Topic> {
    return this.http.post<Topic>(this.topicApiUrl, topic);
  }

  /** DELETE: delete the topic from the server */
  deleteTopic(topic: Topic): Observable<Topic> {
    return this.http.delete<Topic>(this.topicApiUrl + `${topic.topicId}`);
  }

  /* GET topics whose name contains search term */
  searchTopics(term: string): Observable<Topic[]> {
    if (!term.trim()) {
      // if not search term, return empty topic array.
      return of([]);
    }
    return this.http.get<Topic[]>(this.topicApiUrl + `name/${term}`);
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
