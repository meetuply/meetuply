import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, throwError, of} from "rxjs";
import {environment} from "../../environments/environment";
import {Ban} from "../_models/ban";

@Injectable({providedIn: 'root'})
export class BanService {
  private banApiUrl = `${environment.apiUrl}/api/bans/`;

  constructor(private http: HttpClient) {
  }

  /** POST: add a new ban to the server */
  createBan(ban: Ban, reportedId: number, reasonId: number): Observable<Ban> {
    return this.http.post<Ban>(this.banApiUrl + `reported=${reportedId}&reason=${reasonId}`, ban);
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
