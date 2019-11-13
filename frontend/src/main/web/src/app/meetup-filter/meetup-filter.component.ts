import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TopicService } from '../_services'
import { Topic } from '../_models'
import {Filter} from "../_models/filter";
import {FilterService} from "../_services/filter.service";
import {MeetupListItem} from "../_models/meetupListItem";
@Component({
  selector: 'app-meetup-filter',
  templateUrl: './meetup-filter.component.html',
  styleUrls: ['./meetup-filter.component.less']
})

export class MeetupFilterComponent implements OnInit {

  filter_name: string;
  filter_ratingFrom: number;
  filter_ratingTo: number;
  filter_start_date: string;
  filter_start_time: string;
  filter_end_date: string;
  filter_end_time: string;
  topics: Topic[];
  @Output() meetups: EventEmitter<MeetupListItem[]> = new EventEmitter<any>();
  // @Output() meetups : EventEmitter<Meetup[]> = new EventEmitter();
  // selectedTopics = new Set<Topic>();
  selectedTopics : Topic[] = [];
  filters: Filter[];
  selectedFilters = new Set<number>();
  selectedFilter: number;

  weekdayStates:boolean[] = [
    false,
    false,
    false,
    false,
    false,
    false,
    false
  ];

  submit(){
    this.checkRequiredFields(this.filter_name);
    var start_date = new Date(this.filter_start_date + 'T' + this.filter_start_time);
    var end_date = new Date(this.filter_end_date + 'T' + this.filter_end_time);
    // alert(start_date);
    // console.log(end_date);
    var filter: Filter = {
      id: 0,
      name: this.filter_name,
      ratingFrom: this.filter_ratingFrom,
      ratingTo: this.filter_ratingTo,
      dateFrom: start_date,
      dateTo: end_date,
      userId: 0,
      topics: this.selectedTopics
    };
    this.filterService.create(filter).subscribe(data => {
      console.log(data);
      this.filters.unshift(data);
    }, error1 => {
      alert('Some thing happened:' + error1)
    });
    // this.filters.unshift(filter);
    this.filter_name = "";
    this.selectedTopics = [];
    // this.loadFilters();
  }

  search(){
    this.filterService.getMeetupsByFilter(this.selectedFilter).subscribe(data =>
      this.meetups.emit(data)
    );
    console.log(this.meetups)
    this.selectedFilters.clear();
    this.selectedFilters.forEach(x => {
    })


  }

  constructor(
    private topicService: TopicService,
    private filterService: FilterService) {

  }

  checkRequiredFields(input) {
    if(input === null) {
      throw new Error("Attribute 'name' is required");
    }
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
    var selected : Topic[];
    selected = this.topics.filter(x => x.topicId == $event[0]);
    var elem = selected.pop();
    if(this.selectedTopics.includes(elem)){
      this.selectedTopics.splice(this.selectedTopics.indexOf(elem), 1);
    }
    else {
      this.selectedTopics.push(elem);
    }
    // this.selectedTopics.push(selected.pop())
    console.log(this.selectedTopics)
  }

  filterToggled($event) {
    this.selectedFilter = parseInt($event[0]);
    console.log(this.selectedFilter)
  }


  isDate(f: string) {
    var d = new Date(f);
    return d instanceof Date;
  }


  startDate() {
    return new Date(this.filter_start_date + 'T' + this.filter_start_time);
  }

  endDate() {
    return new Date(this.filter_end_date + 'T' + this.filter_end_time);
  }

  validDates() {

    if (this.isDate(this.filter_start_date.toString()) == false || this.isDate(this.filter_end_date.toString()) == false) {
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
    if (this.isDate(this.filter_start_date.toString()) == false || this.isDate(this.filter_end_date.toString()) == false) {
      return "Invalid dates"; //0 invalid dates
    }
    if (this.startDate() > this.endDate()) {
      return "Start date is bigger then end date"; //1 star is bigger then end
    }
    if (this.startDate().setTime(this.startDate().getTime() + (60 * 60 * 1000)) < Date.now()) {
      return "Start date has already passed"; //2 start is already ended
    }
  }

}
