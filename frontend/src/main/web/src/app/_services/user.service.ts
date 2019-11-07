
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
  public currentUser: User;

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  register(user: User): Observable<{}> {
    return this.http.post(this.userApiUrl + 'register', user);
  }

  //get speakers (sonly users who made at least 1 meetup ) for now all users

  getCurrentUser(): Observable<User> {
    return this.get(this.currentUser.userId);
  }

  get(id: number): Observable<User> {
    return this.http.get<User>(this.userApiUrl + `${id}`);
  }

  getAll(): Observable<User[]> {
    return this.http.get<User[]>(this.userApiUrl + 'members')
  }

  getChunk(start: number, size: number): Observable<User[]> {
    return this.http.get<User[]>(this.userApiUrl + 'members/' + start + "/" + size)
  }

  getChunkForAdmin(start: number, size: number): Observable<User[]> {
    return this.http.get<User[]>(this.userApiUrl + 'members/all/' + start + "/" + size)
  }

  getUserFollowers(userId: number): Observable<number[]> {
    return this.http.get<number[]>(this.userApiUrl + `${userId}/subscribers`);
  }

  getUserFollowings(userId: number): Observable<number[]> {
    return this.http.get<number[]>(this.userApiUrl + `${userId}/subscribtions`);
  }

  getUserFollowingsList(userId: number): Observable<User[]> {
    return this.http.get<User[]>(this.userApiUrl + `${userId}/subscribtions/users`);
  }

  getRooms(userId: number): Observable<number[]> {
    return this.http.get<number[]>(this.userApiUrl + userId + "/rooms");
  }

  getRoomsThumbnails(userId: number): Observable<ChatThumbnail[]> {
    return this.http.get<ChatThumbnail[]>(this.userApiUrl + userId + "/roomsList");
  }

  getNotifications(userId: number): Observable<Notification[]> {
    return this.http.get<Notification[]>(this.userApiUrl + userId + "/notifications");
  }

  getUnreadNotifications(userId: number): Observable<Notification[]> {
    return this.http.get<Notification[]>(this.userApiUrl + userId + "/notifications/unread");
  }

  getReadNotifications(userId: number): Observable<Notification[]> {
    return this.http.get<Notification[]>(this.userApiUrl + userId + "/notifications/read");
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

  unfollow(userId: number): Observable<{}> {
    return this.http.delete(this.userApiUrl + 'following/' + userId)
  }

  follow(userId: number): Observable<{}> {
    return this.http.post(this.userApiUrl + 'following/' + userId, {});
  }

  deactivate(userId: number): Observable<{}> {
    return this.http.put(this.userApiUrl + 'deactivate/' + userId, {});
  }

  reactivate(userId: number): Observable<{}> {
    return this.http.put(this.userApiUrl + 'activate/' + userId, {});
  }

  recover(user: User, token: string): Observable<{}> {
    return this.http.patch(this.userApiUrl + (token ? "recover?token=" + token : ""), user);
  }

  requestRecover(email: string) {
    return this.http.get(this.userApiUrl + "recover?email=" + email);
  }

  update(user: User): Observable<{}> {
    return this.http.put(this.userApiUrl, user);
  }

  tokenExsists(token: string) {
    return this.http.get(this.userApiUrl + "token/" + token);
  }
}
