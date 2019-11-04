
import { Injectable } from "@angular/core";
import { User } from "../_models";
import { Language } from "../_models";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { Observable, throwError } from "rxjs";
import { environment } from "../../environments/environment";

import { map } from 'rxjs/operators';
import { AuthenticationService } from "./authentication.service";
import { ChatThumbnail } from '../_models/chatThumbnail'

@Injectable({ providedIn: 'root' })
export class UserService {

  private userApiUrl = `${environment.apiUrl}/api/user/`;
  //private userApiUrl = `http://localhost:8080/api/user/`;
  public currentUser: User;/* = {
    firstName: "sasha",
    lastName: "faryna",
    password: "k",
    confirmedPassword: "k",
    description: "esc",
    email: "email",
    location: "kyiv",
    photo: "assets/img/download.png",
    userId: 2
  }*/

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  register(user: User): Observable<{}> {
    return this.http.post(this.userApiUrl + 'register', user);
  }

  //get speakers (sonly users who made at least 1 meetup ) for now all users

  get(id: number): Observable<User> {
    return this.http.get<User>(this.userApiUrl + `${id}`);
  }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(this.userApiUrl + 'members')
  }



  getChunk(start: number, size: number): Observable<User[]> {
    return this.http.get<User[]>(this.userApiUrl + 'members/' + start + "/" + size)
  }

  getUserFollowers(userId: number): Observable<number[]> {
    return this.http.get<number[]>(this.userApiUrl + `${userId}/subscribers`);
  }

  getRooms(userId: number): Observable<number[]> {
    return this.http.get<number[]>(this.userApiUrl + userId + "/rooms");
  }

  getRoomsThumbnails(userId: number): Observable<ChatThumbnail[]> {
    return this.http.get<ChatThumbnail[]>(this.userApiUrl + userId + "/roomsList");
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

  activate(token: string): Observable<{}> {
    return this.http.get(this.userApiUrl + 'confirm?token=' + token);
  }

  getUserLanguages(userId: number): Observable<Language[]> {
    return this.http.get<Language[]>(this.userApiUrl + `${userId}/languages`);
  }
}
