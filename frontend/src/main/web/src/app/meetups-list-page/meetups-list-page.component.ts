import {Component, OnDestroy, OnInit} from '@angular/core';
import {MeetupListItem} from "../_models/meetupListItem"
import {MeetupService} from "../_services/meetup.service";
import {Subscription} from "rxjs";
import {UserService} from "../_services";
import {StateService} from "../_services/state.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-meetups-list-page',
  templateUrl: './meetups-list-page.component.html',
  styleUrls: ['./meetups-list-page.component.less']
})

export class MeetupsListPageComponent implements OnInit, OnDestroy {

  loading = false;
  lastRow = 0;
  maxMeetupsOnPage: number;
  meetupsChunkSize = 10;
  scrollDistance = 2;
  meetupsList: MeetupListItem[] = [];
  private sub: Subscription;
  filter_shown = false;
  author: string;
  userID: number;

  constructor(private router: Router,
              public userService: UserService,
              private meetupService: MeetupService,
              public stateService: StateService) {
    this.userID = this.userService.currentUser.userId;
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
      this.meetupService.getMeetupsChunkWithUsernameAndRating(this.lastRow, this.meetupsChunkSize).toPromise().then(
        data => {
          this.loading = false;
          if (data) {
            this.lastRow += data.length;
            this.meetupsList.push(...data);
          }
        }, error1 => {
          console.log(error1);
        })
    }
  }

  addButtonClicked(event) {
    this.router.navigate(["/create/meetup"]);
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }
}
