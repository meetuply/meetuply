import { Component, OnInit, Input } from '@angular/core';


@Component({
  selector: 'app-speaker-list-item',
  templateUrl: './speaker-list-item.component.html',
  styleUrls: ['./speaker-list-item.component.css']
})
export class SpeakerListItemComponent implements OnInit {


  @Input() name: string;
  @Input() surname: string;
  @Input() location: string;
  @Input() rate: number;
  @Input() following: Boolean;
  @Input() awards: number;
  @Input() languages: Array<string>;
  @Input() description: string;
  @Input() id: number;

  followText(): string {


    if (this.following === true) {
      return "Followed";
    }
    return "Follow";

  }

  followType(): number {
    if (this.following === true) {
      return 2;
    }
    return 1;
  }

  constructor() { }

  ngOnInit() {

  }

  link():string {
    return "/speakers/" + this.id;
  }

}
