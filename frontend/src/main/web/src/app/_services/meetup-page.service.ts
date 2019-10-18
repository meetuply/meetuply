import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
// import { config } from '../../default.config';
import {Meetup} from "../_models/meetup";
import {environment} from "../../environments/environment";
import {UserService} from "./user.service";
import {User} from "../_models";

@Injectable({ providedIn: 'root' })
export class MeetupPageService {

  private meetupApiUrl = `${environment.apiUrl}/api/meetups/`;

  constructor(private http: HttpClient, private userService: UserService) {
  }

  get(id:number) : Observable<Meetup>{
    console.log("loading meetup in service");
    return this.http.get<Meetup>(this.meetupApiUrl+`${id}`);
  }

  getSpeaker(id:number) : Observable<User>{
    return this.userService.get(id);
  }

  getAttendees(meetupId: number): Observable<User[]>{
    return this.http.get<User[]>(this.meetupApiUrl+`${meetupId}`+'/attendees');
  }
}
