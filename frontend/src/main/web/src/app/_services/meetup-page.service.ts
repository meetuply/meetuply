import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
// import { config } from '../../default.config';
import {Meetup} from "../_models/meetup";
import {environment} from "../../environments/environment";
import {UserService} from "./user.service";
import {User} from "../_models";

@Injectable({ providedIn: 'root' })
export class MeetupPageService {
  // private getUrl    = '/meetups/:id';
  // private updateUrl = config.api_path + '/meetups/update/';
  // private deleteUrl = config.api_path + '/meetups/delete/';

  constructor(private http: HttpClient, private userService: UserService) {
  }

  get(id:number) : Observable<Meetup>{
    console.log("loading meetup in service");
    return this.http.get<Meetup>(`${environment.apiUrl}/api/meetups/${id}`);
  }

  getSpeaker(id:number) : Observable<User>{
    return this.userService.get(id);
  }
}
