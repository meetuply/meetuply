import { Component, OnInit, Input } from '@angular/core';
import { Atendee } from '../_models/atendee'

@Component({
  selector: 'app-meetup-page',
  templateUrl: './meetup-page.component.html',
  styleUrls: ['./meetup-page.component.less']
})
export class MeetupPageComponent implements OnInit {


  //TODO: retrieve from backend using specific meetup id or other

  title = 'James char lecture';
  author = 'james char';
  location = 'location';
  rate = 4;

  description = "wurecbhih debrecbhih brecbhibrecbbrecbhih dbrecbrecbhih debrecbhih debrecbhih debrecbhih debrecbhih debrecbhih debhih brecbhih debrecbhih de debrecbhih debrecbhih deebrecbhih dehih debrecbhih debrecbhih debrecbhih debrecbhih debrecbhih dedebrbrecbhih debrecbhih deecbhihbrecbbrecbhih debrecbhih dedebrecbhih debrecbhih debrecbhih de debrecbhih deebrecbhih debrecbhih de debrecbhih derecbrebrecbhih debrecbhih debhih debrecbhih debhih debrecbhih debrecbhih debrecbhih debrecbhih debrecbhih debrecbhih debrecbhih debrecbhih debrecbhih debi";
  time = "15:00";
  date = "13/10/19";
  maxMembers = 20;
  members = 17;
  joined = true;
  id = 0;

  atendees: Atendee[] = [
    {
      name: "john",
      surname: "dee",
      rating: 4
    },
    {
      name: "johwefn",
      surname: "dee",
      rating: 5
    },
    {
      name: "joker",
      surname: "lol",
      rating: 1
    }
  ]

  constructor() { }

  ngOnInit() {
  }


  joinType() {
    return (this.joined == true ? '2' : (this.maxMembers == this.members ? "3" : "1"));
  }

  joinText() {
    return (this.joined == true ? 'Leave' : (this.maxMembers == this.members ? "Full" : "Join"));
  }


  goBack() {

  }

}
