import {Component, Input, OnInit} from '@angular/core';
import {Meetup_list_item} from "../_models/meetup_list_item"
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
  meetupsList: Meetup_list_item[] = [];
  newChunk: Meetup_list_item[];
  private sub: Subscription;
  filter_shown = false;
  author: string;
  userID: number;

  constructor(private userService: UserService,
              private meetupService: MeetupService,
              private ratingService: RatingService) {
    this.userID = this.userService.currentUser.userId;
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
    console.log("LAST ROW: ");
    console.log(this.lastRow);
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
              return new Meetup_list_item(item, username, photo, rating)
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
