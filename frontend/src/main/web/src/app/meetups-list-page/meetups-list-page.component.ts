import { Component, OnInit } from '@angular/core';
import { Meetup_list_item } from "../_models/meetup_list_item"

@Component({
  selector: 'app-meetups-list-page',
  templateUrl: './meetups-list-page.component.html',
  styleUrls: ['./meetups-list-page.component.less']
})
export class MeetupsListPageComponent implements OnInit {


  //TODO: service to retrieve meetups from backend
  meetups_list: Meetup_list_item[] = [

    {
      author: "James charles",
      joined: false,
      time: '12:30',
      date: '19/12/19',
      rate: 4,
      description: "ererteivefujdbersujicnwucyadcyn",
      title: 'cool',
      location: 'office',
      maxMembers: 15,
      members: 15,
      id: 1
    }

  ]


  filter_shown = false;

  constructor() { }

  ngOnInit() {
  }

  toggleFilters() {
    this.filter_shown = !this.filter_shown;
  }

  getArrowClass() {
    return 'arrow_icon ' + (this.filter_shown ? 'up' : 'down');
  }

  isOdd(num: number): boolean {
    return num % 2 == 0;
  }

}
