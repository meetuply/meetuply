import { Injectable } from "@angular/core";
import {Filter} from "../_models/filter";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { Observable, throwError } from "rxjs";
import { environment } from "../../environments/environment";
import {Meetup} from "../_models/meetup";


@Injectable({ providedIn: 'root' })
export class FilterService {


  private filterApiUrl = `${environment.apiUrl}/api/meetups/`;

  constructor(private http: HttpClient) { }

  create(filter: Filter): Observable<{}> {
    return this.http.post(this.filterApiUrl+ "filters", filter);
  }

  getAll():Observable<Filter[]> {
    return this.http.get<Filter[]>(this.filterApiUrl);
  }

  getUsersFilters():Observable<Filter[]>{
    return this.http.get<Filter[]>(this.filterApiUrl+"userFilters")
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
