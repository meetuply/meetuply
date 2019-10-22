import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Meetup} from "../_models/meetup";
import {environment} from "../../environments/environment";
import {UserService} from "./user.service";
import {User} from "../_models";

@Injectable({ providedIn: 'root' })
export class MeetupService {

  private meetupApiUrl = `${environment.apiUrl}/api/meetups/`;

  constructor(private http: HttpClient, private userService: UserService) {
  }

  get(id:number) : Observable<Meetup>{
    console.log("loading meetup in service");
    return this.http.get<Meetup>(this.meetupApiUrl+`${id}`);
  }

  getAttendees(meetupId: number): Observable<User[]>{
    return this.http.get<User[]>(this.meetupApiUrl+`${meetupId}`+'/attendees');
  }

  getMeetupsChunk(startRow: number, endRow: number): Observable<Meetup[]>{
    return this.http.get<Meetup[]>(this.meetupApiUrl+`${startRow}`+"/"+`${endRow}`)
  }
}
