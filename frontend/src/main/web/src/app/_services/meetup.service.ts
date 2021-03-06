import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Meetup } from "../_models/meetup";
import { environment } from "../../environments/environment";
import { UserService } from "./user.service";
import {Topic, User} from "../_models";
import {MeetupListItem} from "../_models/meetupListItem";


@Injectable({ providedIn: 'root' })
export class MeetupService {

  private meetupApiUrl = `${environment.apiUrl}/api/meetups/`;

  constructor(private http: HttpClient, private userService: UserService) {
  }

  getMeetupsChunk(startRow: number, endRow: number): Observable<Meetup[]> {
    return this.http.get<Meetup[]>(this.meetupApiUrl + `${startRow}` + "/" + `${endRow}`)
  }

  getMeetupsChunkWithUsernameAndRating(startRow: number, endRow: number): Observable<MeetupListItem[]> {
    return this.http.get<MeetupListItem[]>(this.meetupApiUrl + `${startRow}` + "/" + `${endRow}`)
  }

  getMeetupsChunkActive(startRow: number, endRow: number): Observable<MeetupListItem[]> {
    return this.http.get<MeetupListItem[]>(this.meetupApiUrl + "active/" + `${startRow}` + "/" + `${endRow}`)
  }

  getUserMeetupsChunk(startRow: number, endRow: number): Observable<MeetupListItem[]> {
    return this.http.get<MeetupListItem[]>(this.meetupApiUrl + "user/" + `${startRow}` + "/" + `${endRow}`)
  }

  getFutureMeetups(userId: number): Observable<Meetup[]> {
    return this.http.get<Meetup[]>(this.meetupApiUrl + "future/"+userId)
  }

  getSoonMeetups(userId: number, day: number): Observable<MeetupListItem[]> {
    return this.http.get<MeetupListItem[]>(this.meetupApiUrl + "soon/"+userId+"/"+day)
  }

  getPastMeetups(userId: number): Observable<Meetup[]> {
    return this.http.get<Meetup[]>(this.meetupApiUrl + "past/"+userId)
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

  getTopics(meetupId: number): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.meetupApiUrl + `${meetupId}` + '/topics');
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
    return this.http.post(this.meetupApiUrl, meetup);
  }

  terminate(meetupID: number): Observable<{}> {
    return this.http.patch(this.meetupApiUrl + `${meetupID}` + '/action=terminate', null);
  }

  cancell(meetupID: number): Observable<{}> {
    return this.http.patch(this.meetupApiUrl + `${meetupID}` + '/action=cancel', null);
  }

  reschedule(meetup: Meetup): Observable<{}> {
    return this.http.patch(this.meetupApiUrl + `${meetup.meetupId}` + '/action=reschedule', meetup);
  }
}
