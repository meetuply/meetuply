import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common'
import {Router} from '@angular/router'

import {LanguageService} from '../_services/language.service'
import {Topic} from '../_models'
import {TopicService} from '../_services/topic.service'
import {MeetupService} from '../_services/meetup.service';
import {Meetup} from '../_models/meetup'

@Component({
  selector: 'app-create-meetup-page',
  templateUrl: './create-meetup-page.component.html',
  styleUrls: ['./create-meetup-page.component.less']
})
export class CreateMeetupPageComponent implements OnInit {

  constructor(private _location: Location, private router: Router, private languageService: LanguageService, private topicService: TopicService, private meetupService: MeetupService) { }

  meetup_title: string;
  meetup_description: string;
  meetup_location: string;
  meetup_max_atendees: number;
  meetup_start_date: string;
  meetup_start_time: string;
  meetup_end_date: string;
  meetup_end_time: string;
  languages: String[];
  topics: Topic[];
  selectedTopics = new Set();

  topicToggled($event) {
    if ($event[1] == true) {
      this.selectedTopics.add($event[0])
    } else {
      this.selectedTopics.delete($event[0]);
    }
    console.log(this.selectedTopics)

  }

  loadTopics() {
    this.topicService.getAll().subscribe(topicsList =>
      this.topics = topicsList
    )
  }

  submit($event) {
    var start_date = new Date(this.meetup_start_date + 'T' + this.meetup_start_time);
    var end_date = new Date(this.meetup_end_date + 'T' + this.meetup_end_time);
    var meetup: Meetup = {
      meetupId: 0,
      speakerId: 0,
      stateId: 0,
      meetupDescription: this.meetup_description,
      meetupTitle: this.meetup_title,
      meetupPlace: this.meetup_location,
      meetupMinAttendees: 0,
      meetupMaxAttendees: this.meetup_max_atendees,
      meetupRegisteredAttendees: 0,
      meetupStartDateTime: start_date,
      meetupFinishDateTime: end_date
    };
    this.meetupService.create(meetup).subscribe(data => {
      window.location.href ='/meetups';
    }, error => {
      alert(error)
    });
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
