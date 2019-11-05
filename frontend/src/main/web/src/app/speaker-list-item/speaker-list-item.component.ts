import {Component, OnInit, Input} from '@angular/core';
import {UserService} from "../_services";
import {Speaker_list_item} from "../_models/speaker_list_item";


@Component({
  selector: 'app-speaker-list-item',
  templateUrl: './speaker-list-item.component.html',
  styleUrls: ['./speaker-list-item.component.css']
})
export class SpeakerListItemComponent implements OnInit {

  @Input() speaker_list_item: Speaker_list_item;
  currentUser: number;
  error;

  followText(): string {
    if (this.speaker_list_item.following === true) {
      return "Unfollow";
    }
    return "Follow";

  }

  followType(): number {
    if (this.speaker_list_item.following === true) {
      return 2;
    }
    return 1;
  }

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.currentUser = this.userService.currentUser.userId;
  }

  link(): string {
    return "/speakers/" + this.speaker_list_item.id;
  }

  followButtonClicked(event) {
    if (this.speaker_list_item.following)
      this.userService.unfollow(this.speaker_list_item.id).subscribe(
        data => {
          this.speaker_list_item.following = false;
        },
        error => {
          this.error = error;
        }
      );
    else
      this.userService.follow(this.speaker_list_item.id).subscribe(
        data => {
          this.speaker_list_item.following = true;
        },
        error => {
          this.error = error;
        }
      );
  }

}
