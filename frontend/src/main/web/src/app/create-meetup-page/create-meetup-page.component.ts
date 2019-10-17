import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common'
import { Router } from '@angular/router'
import { TopicService } from '../_services/topic.service'
import { Topic } from '../_models/topic'

@Component({
  selector: 'app-create-meetup-page',
  templateUrl: './create-meetup-page.component.html',
  styleUrls: ['./create-meetup-page.component.less']
})
export class CreateMeetupPageComponent implements OnInit {

  constructor(private _location: Location, private router: Router, private topicService: TopicService) { }

  //make services to retrieve languages list and topics list from server


  languages: String[] = [
    'English',
    'Spanish',
    'German'
  ]

  topics: Topic[];

  topicToggled($event) {

  }

  ngOnInit() {

    this.topicService.getTopics().subscribe(topicsList => {
      this.topics = topicsList
    }
    );

  }


  goBack() {
    this._location.back();
  }

}
