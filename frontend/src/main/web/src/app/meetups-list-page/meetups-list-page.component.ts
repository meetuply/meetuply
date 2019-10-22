import {Component, Input, OnInit} from '@angular/core';
import {Meetup_list_item} from "../_models/meetup_list_item"
import {MeetupService} from "../_services/meetup.service";
import {Subscription} from "rxjs";
import {UserService} from "../_services";
import {Meetup} from "../_models/meetup";

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
  //todo add author, rating
  author: string;

  constructor(private userService: UserService,
              private meetupService: MeetupService) {
  }

  ngOnInit() {
    this.maxMeetupsOnPage = 10;
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

  // getAuthorName(id: number, model) {
  //   if (model == null){
  //     model = { author : ""}
  //   }
  //   this.loading = true;
  //   this.userService.get(id).subscribe(
  //     data => {
  //       this.loading = false;
  //       console.log("In method");
  //       console.log(data);
  //       model.author = data['firstName']+" " + data['lastName'];},
  //     error1 => {
  //       this.loading = false;
  //     }
  //   );
  //   return model;
  // }

  loadMeetupsChunk() {
    console.log("LAST ROW: ");
    console.log(this.lastRow);
    if (this.lastRow < this.maxMeetupsOnPage) {
      this.loading = true;
      this.sub = this.meetupService.getMeetupsChunk(this.lastRow, this.step).subscribe(
        data => {
          this.loading = false;
          this.lastRow += this.step;
          this.newChunk = data.map(item => {
              return new Meetup_list_item(
                item,
                item.meetupStartDateTime.substring(0, 10),
                item.meetupStartDateTime.substring(11, 16),
                true,
                3,
                this.userService
              )
            }
          );
          this.meetupsList.push(...this.newChunk);
        },
        error1 => {
          this.loading = false;
        }
      )
    }
  }
}
