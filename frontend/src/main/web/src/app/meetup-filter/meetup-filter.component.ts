import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-meetup-filter',
  templateUrl: './meetup-filter.component.html',
  styleUrls: ['./meetup-filter.component.less']
})


export class MeetupFilterComponent implements OnInit {

  text1 = "lol11w32f3"

  weekdayStates:boolean[] = [
    false,
    false,
    false,
    false,
    false,
    false,
    false
  ];


  constructor() { }

  ngOnInit() {
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
