import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-meetup-list-item',
  templateUrl: './meetup-list-item.component.html',
  styleUrls: ['./meetup-list-item.component.less']
})
export class MeetupListItemComponent implements OnInit {

  @Input() title: string;
  @Input() author: string;
  @Input() location: string;
  @Input() rate: number;

  @Input() description: string;
  @Input() time: string;
  @Input() date: string;
  @Input() maxMembers: number;
  @Input() members: number;
  @Input() joined: boolean;
  @Input() id: number;



  constructor() {
  }

  ngOnInit() {
    
  }


  joinType() {
    return (this.joined == true ? '2' : (this.maxMembers == this.members ? "3" : "1"));
  }

  joinText() {
    return (this.joined == true ? 'Leave' : (this.maxMembers == this.members ? "Full" : "Join"));
  }

}
