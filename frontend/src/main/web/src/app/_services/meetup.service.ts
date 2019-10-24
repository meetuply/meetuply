import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Meetup } from "../_models/meetup";
import { environment } from "../../environments/environment";
import { UserService } from "./user.service";
import { User } from "../_models";


@Injectable({ providedIn: 'root' })
export class MeetupService {

  //private meetupApiUrl = `${environment.apiUrl}/api/meetups/`;
  private meetupApiUrl = `http://localhost:8080/api/meetups/`;

  constructor(private http: HttpClient, private userService: UserService) {
  }

  getMeetupsChunk(startRow: number, endRow: number): Observable<Meetup[]> {
    return this.http.get<Meetup[]>(this.meetupApiUrl + `${startRow}` + "/" + `${endRow}`)
  }

  get(id: number): Observable<Meetup> {
    console.log("loading meetup in service");
    return this.http.get<Meetup>(this.meetupApiUrl + `${id}`);
  }

  getSpeaker(id: number): Observable<User> {
    return this.userService.get(id);
  }

  getAttendees(meetupId: number): Observable<User[]> {
    return this.http.get<User[]>(this.meetupApiUrl + `${meetupId}` + '/attendees');
  }

  joinMeetup(meetupID: number): Observable<{}> {
    return this.http.put(this.meetupApiUrl + `${meetupID}` + '/join', {});
  }

  leaveMeetup(meetupID: number): Observable<{}> {
    return this.http.delete(this.meetupApiUrl + `${meetupID}` + '/leave');
  }

  isAttendee(meetupID: number, userID: number): Observable<boolean> {
    return this.http.get<boolean>(this.meetupApiUrl + + `${meetupID}` + '/attendee',
      { params: { "id": `${userID}` } });
  }

  create(meetup: Meetup): Observable<{}> {
    return this.http.post(this.meetupApiUrl + 'create', meetup);
  }
}
