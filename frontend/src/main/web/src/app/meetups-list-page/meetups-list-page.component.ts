import {Component, Input, OnDestroy, OnInit} from '@angular/core';
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

export class MeetupsListPageComponent implements OnInit, OnDestroy {

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
    this.maxMeetupsOnPage = 1600;
    this.loadMeetupsChunk();
  }

  onScrollDown() {
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
      this.meetupService.getMeetupsChunkWithUsernameAndRating(this.lastRow, this.step).toPromise().then(
          data => {
          this.loading = false;
          if (data) {
            console.log(data);
            this.lastRow += data.length;
            this.meetupsList.push(...data);
          }
        }, error1 => {
          console.log(error1);
        })
    }
  }

  ngOnDestroy(): void {
    if (this.sub){
      this.sub.unsubscribe();
    }
  }
}
