import { Injectable } from "@angular/core";
import { Language } from "../_models/language";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { Observable, throwError } from "rxjs";
import { environment } from "../../environments/environment";


@Injectable({ providedIn: 'root' })
export class LanguageService {


  private languageApiUrl = `${environment.apiUrl}/api/languages/`;
  //private languageApiUrl = 'http://localhost:8080/api/languages';

  constructor(private http: HttpClient) { }


  get(id: number): Observable<Language> {
    return this.http.get<Language>(this.languageApiUrl + `${id}`);
  }

  getAll():Observable<Language[]> {
    return this.http.get<Language[]>(this.languageApiUrl);
  }

  updateUser(userId, languages):Observable<{}> {
    return this.http.post(this.languageApiUrl + `user/${userId}`, languages);
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
