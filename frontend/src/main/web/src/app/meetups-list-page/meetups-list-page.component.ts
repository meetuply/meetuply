import {Component, Input, OnInit} from '@angular/core';
import {MeetupListItem} from "../_models/meetupListItem"
import {MeetupService} from "../_services/meetup.service";
import {Subscription} from "rxjs";
import {UserService} from "../_services";
import {Meetup} from "../_models/meetup";
import {RatingService} from "../_services/rating.service";

@Component({
  selector: 'app-meetups-list-page',
  templateUrl: './meetups-list-page.component.html',
  styleUrls: ['./meetups-list-page.component.less']
})

export class MeetupsListPageComponent implements OnInit {

  loading = false;
  lastRow = 0;
  maxMeetupsOnPage: number;
  step = 4;
  scrollDistance = 2;
  meetupsList: MeetupListItem[] = [];
  newChunk: MeetupListItem[];
  private sub: Subscription;
  filter_shown = false;
  author: string;

  constructor(private userService: UserService,
              private meetupService: MeetupService,
              private ratingService: RatingService) {
  }

  ngOnInit() {
    this.maxMeetupsOnPage = 50;
    this.loadMeetupsChunk();
  }

  onScrollDown() {
    console.log('scrolled!!');
    this.loadMeetupsChunk();
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

  loadMeetupsChunk() {
    if (this.lastRow < this.maxMeetupsOnPage) {
      this.loading = true;
      this.sub = this.meetupService.getMeetupsChunk(this.lastRow, this.step).subscribe(
        async data => {
          this.loading = false;
          if (data){
            this.lastRow += data.length;
          this.newChunk = await Promise.all(data.map( async item => {
              let username = "";
              let photo = "";
              let rating = 0;
              await this.userService.get(item.speakerId).toPromise().then(
                speaker => {
                  username = speaker.firstName + " " + speaker.lastName;
                  photo = speaker.photo;
                }
              );
              await this.ratingService.getUserRatingAvg(item.speakerId).toPromise().then(
                rate => {rating = rate}
              );
              return new MeetupListItem(item, username, photo, rating)
            }
          ));
          this.meetupsList.push(...this.newChunk);
        }
        },
        error1 => {
          this.loading = false;
        }
      )
    }
  }
}
