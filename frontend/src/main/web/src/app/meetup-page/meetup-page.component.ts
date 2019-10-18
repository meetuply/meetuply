import { Component, OnInit, Input } from '@angular/core';
import { Atendee } from '../_models/atendee'
import { Meetup } from "../_models/meetup";
import {AuthenticationService} from "../_services";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {MeetupPageService} from "../_services/meetup-page.service";
import {tap} from "rxjs/operators";
import {Observable} from "rxjs";

@Component({
  selector: 'app-meetup-page',
  templateUrl: './meetup-page.component.html',
  styleUrls: ['./meetup-page.component.less']
})
export class MeetupPageComponent implements OnInit {

  loading = false;
  private sub: any;
  id: number;
  speakerId: number;
  // meetup: Meetup = new Meetup();
  //TODO: retrieve from backend using specific meetup id or other
  title: string;
  location: string;
  description: string;
  date: string;
  time: string;
  maxMembers: number;
  members: number;
  author: string;
  // rate = 4;
  joined = true;
  error = null;

  atendees: Atendee[] = [
    {
      name: "john",
      surname: "doe2",
      rating: 4
    },
    {
      name: "Kate",
      surname: "Tonyik",
      rating: 5
    },
    {
      name: "Jocker",
      surname: "Jocker",
      rating: 1
    }
  ]

  constructor(
    private meetupPageService: MeetupPageService,
    private router: Router,
    private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.loadMeetup(this.id);
  }

  loadMeetup(id:number) {
    this.loading = true;
    this.meetupPageService.get(id).subscribe(
      data => {
        this.title = data['meetupTitle'];
        this.description = data['meetupDescription'];
        this.date = data['meetupStartDateTime'].substring(0, 10);
        this.time = data['meetupStartDateTime'].substring(11, 16);
        this.location = data['meetupPlace'];
        this.maxMembers = data['meetupMaxAttendees'];
        this.members = data['meetupRegisteredAttendees'];
        this.speakerId = data['speakerId'];
        this.getMeetupSpeakerName();
      }, error => this.error = error);
  }

  getMeetupSpeakerName(){
    this.meetupPageService.getSpeaker(this.speakerId).subscribe(
      data => {
        this.author = data['firstName'] +" "+ data['lastName'];
      }
    );
  }

  joinType() {
    return (this.joined == true ? '2' : (this.maxMembers == this.members ? "3" : "1"));
  }

  joinText() {
    return (this.joined == true ? 'Leave' : (this.maxMembers == this.members ? "Full" : "Join"));
  }

  ngOnDestroy(){
    this.sub.unsubscribe();
  }

  goBack() {

  }

}
