import { Component, OnInit } from '@angular/core';
import { TopicService } from '../_services/topic.service'
import { Topic } from '../_models'
import {Filter} from "../_models/filter";
import {FilterService} from "../_services/filter.service";

@Component({
  selector: 'app-meetup-filter',
  templateUrl: './meetup-filter.component.html',
  styleUrls: ['./meetup-filter.component.less']
})


export class MeetupFilterComponent implements OnInit {

  text1 = "lol123";
  topics: Topic[];
  selectedTopics = new Set();
  filters: Filter[];
  selectedFilters = new Set();

  weekdayStates:boolean[] = [
    false,
    false,
    false,
    false,
    false,
    false,
    false
  ];


  constructor(
    private topicService: TopicService,
    private filterService: FilterService) {

  }

  ngOnInit() {
    this.loadTopics();
    this.loadFilters();
  }

  loadTopics() {
    this.topicService.getAll().subscribe(topicsList =>
      this.topics = topicsList
    );
  }

  loadFilters() {
    this.filterService.getUsersFilters().subscribe(filtersList =>
      this.filters = filtersList
    );
  }

  topicToggled($event) {
    if ($event[1] == true) {
      this.selectedTopics.add($event[0]);
    } else {
      this.selectedTopics.delete($event[0]);
    }
    console.log(this.selectedTopics)

  }

  filterToggled($event) {
    if ($event[1] == true) {
      this.selectedFilters.add($event[0])
    } else {
      this.selectedFilters.delete($event[0]);
    }
    console.log(this.selectedFilters)

  }

  toggledWeekday($event) {
    this.weekdayStates[$event[0]] = $event[1]
  }

  toggled($event) {

    if ($event[0] == 1) {
      this.text1 = "" + (Math.random() * 5);
    }


  }

}
