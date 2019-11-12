import {Component, OnInit, Input} from '@angular/core';
import {UserService} from "../_services";
import {SpeakerListItem} from "../_models/speakerListItem";
import {BanService} from "../_services/ban.service";

@Component({
  selector: 'app-speaker-list-item',
  templateUrl: './speaker-list-item.component.html',
  styleUrls: ['./speaker-list-item.component.css']
})
export class SpeakerListItemComponent implements OnInit {

  @Input() speakerListItem: SpeakerListItem;
  error;
  bans: number;

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

  constructor(public userService: UserService,
              private banService: BanService) {
  }

  ngOnInit() {
    this.loadBans(this.speakerListItem.id);
  }

  link(): string {
    return "/speakers/" + this.speakerListItem.id;
  }

  loadBans(id: number) {
    this.banService.get(id).subscribe(bans => this.bans = bans.length);
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
