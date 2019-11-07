import {Component, OnInit, Input} from '@angular/core';
import {UserService} from "../_services";
import {SpeakerListItem} from "../_models/speakerListItem";

@Component({
  selector: 'app-speaker-list-item',
  templateUrl: './speaker-list-item.component.html',
  styleUrls: ['./speaker-list-item.component.css']
})
export class SpeakerListItemComponent implements OnInit {

  @Input() speakerListItem: SpeakerListItem;
  error;

  followText(): string {
    if (this.speakerListItem.following === true) {
      return "Unfollow";
    }
    return "Follow";

  }

  followType(): number {
    if (this.speakerListItem.following === true) {
      return 2;
    }
    return 1;
  }

  constructor(private userService: UserService) {
  }

  ngOnInit() {
  }

  link(): string {
    return "/speakers/" + this.speakerListItem.id;
  }

  followButtonClicked(event) {
    if (this.speakerListItem.following)
      this.userService.unfollow(this.speakerListItem.id).subscribe(
        data => {
          this.speakerListItem.following = false;
        },
        error => {
          this.error = error;
        }
      );
    else
      this.userService.follow(this.speakerListItem.id).subscribe(
        data => {
          this.speakerListItem.following = true;
        },
        error => {
          this.error = error;
        }
      );
  }

  isCurrentUser(){
    return this.userService.currentUser.userId===this.speakerListItem.id
  }

}
