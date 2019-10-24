import { Injectable } from "@angular/core";
import { Topic } from "../_models";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { Observable, throwError } from "rxjs";
import { environment } from "../../environments/environment";


@Injectable({ providedIn: 'root' })
export class TopicService {


  //private languageApiUrl = `${environment.apiUrl}/api/languages/`;
  private topicApiUrl = 'http://localhost:8080/api/topics';

  constructor(private http: HttpClient) { }


  getAll():Observable<Topic[]> {
    return this.http.get<Topic[]>(this.topicApiUrl);
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
