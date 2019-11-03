import { Component, OnInit, Input } from '@angular/core';
import {UserService} from "../_services";

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
  error;

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

  constructor(private userService: UserService) { }

  ngOnInit() {

  }

  link():string {
    return "/speakers/" + this.id;
  }

  followButtonClicked(event){
    if (this.following)
      this.userService.unfollow(this.id).subscribe(
        data => {
          this.following = false;
        },
        error => {
          this.error = error;
        }
      );
    else
      this.userService.follow(this.id).subscribe(
        data => {
          this.following = true;
        },
        error => {
          this.error = error;
        }
      );
  }

}
