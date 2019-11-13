import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common'
import { Router } from '@angular/router'

import { LanguageService } from '../_services/language.service'
import { Topic } from '../_models'
import { TopicService } from '../_services/topic.service'
import { MeetupService } from '../_services/meetup.service';
import { Meetup } from '../_models/meetup'

@Component({
  selector: 'app-create-meetup-page',
  templateUrl: './create-meetup-page.component.html',
  styleUrls: ['./create-meetup-page.component.less']
})
export class CreateMeetupPageComponent implements OnInit {

  constructor(private _location: Location, private router: Router, private languageService: LanguageService, private topicService: TopicService, private meetupService: MeetupService) { }

  meetup_title: string;
  meetup_description: string = ""
  meetup_location: string = "";
  meetup_max_atendees: number;
  meetup_start_date: string;
  meetup_start_time: string;
  meetup_end_date: string;
  meetup_end_time: string;
  languages: string[];
  topics: Topic[];
  selectedTopics = new Set<number>();

  selectedLanguage = 'English';

  topicToggled($event) {
    if ($event[1] == true) {
      this.selectedTopics.add($event[0])
    } else {
      this.selectedTopics.delete($event[0]);
    }
  }

  loadTopics() {
    this.topicService.getAll().subscribe(topicsList => {
      this.topics = topicsList;
    }
    )
  }

  isDate(f: string) {
    var d = new Date(f);
    return d instanceof Date;
  }


  startDate() {
    return new Date(this.meetup_start_date + 'T' + this.meetup_start_time);
  }

  endDate() {
    return new Date(this.meetup_end_date + 'T' + this.meetup_end_time);
  }

  validDates() {

    if (this.isDate(this.meetup_start_date.toString()) == false || this.isDate(this.meetup_end_date.toString()) == false) {
      return 1; //0 invalid dates
    }
    if (this.startDate() > this.endDate()) {
      return 2; //1 star is bigger then end
    }
    if (this.startDate().getTime() <= Date.now()) {
      return 2; //2 start is already ended
    }

    if (this.endDate().getTime() <= Date.now()) {
      return 3; //2 start is already ended
    }

    return 0;
  }

  dateMessage() {
    if (this.isDate(this.meetup_start_date.toString()) == false || this.isDate(this.meetup_end_date.toString()) == false) {
      return "Invalid dates"; //0 invalid dates
    }
    if (this.startDate() > this.endDate()) {
      return "Start date is bigger then end date"; //1 star is bigger then end
    }
    if (this.startDate().setTime(this.startDate().getTime() + (1 * 60 * 60 * 1000)) < Date.now()) {
      return "Start date has already passed"; //2 start is already ended
    }
  }

  submit() {
    var start_date = new Date(this.meetup_start_date + 'T' + this.meetup_start_time);
    var end_date = new Date(this.meetup_end_date + 'T' + this.meetup_end_time);

    var offset = new Date().getTimezoneOffset() / -60;


    var meetup: Meetup = {
      meetupId: 0,
      speakerId: 0,
      stateId: 0,
      meetupDescription: this.meetup_description,
      meetupTitle: this.meetup_title,
      meetupPlace: this.meetup_location,
      meetupMinAttendees: 1,
      meetupMaxAttendees: this.meetup_max_atendees,
      meetupRegisteredAttendees: 0,
      meetupStartDateTime: start_date,
      meetupFinishDateTime: end_date,
      language: this.selectedLanguage,
      topics: Array.from(this.selectedTopics)
    };

    this.meetupService.create(meetup).subscribe(data => {
      this.router.navigate(['meetups']);
    }, error => {
      alert('some thing happened:' + error)
    });
    console.log(meetup)
  }
  loadLanguages() {
    this.languageService.getAll().subscribe(languageList => {
      this.languages = languageList.map(language => language.name)
      if (!this.languages || !this.languages.length) {
        this.languages = [
          'English',
          'Spanish',
          'German'
        ]
      }
      this.selectedLanguage = this.languages[0];
    })
  }

  ngOnInit() {
    this.loadLanguages();
    this.loadTopics();

  }

  goBack() {
    this._location.back();
  }
}
