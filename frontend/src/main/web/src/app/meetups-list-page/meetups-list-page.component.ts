import { Component, OnDestroy, OnInit } from '@angular/core';
import { MeetupListItem } from "../_models/meetupListItem"
import { MeetupService } from "../_services/meetup.service";
import { Subscription } from "rxjs";
import { UserService } from "../_services";
import { StateService } from "../_services/state.service";
import { Router } from "@angular/router";

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
  showActiveFlag = true;
  showAllFlag = false;
  showMyFlag = false;


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

      if (this.showAllFlag) {
        this.meetupService.getMeetupsChunkWithUsernameAndRating(this.lastRow, this.meetupsChunkSize).toPromise().then(
          data => {
            this.loading = false;
            if (data) {
              this.lastRow += data.length;
              console.log(data);
              this.meetupsList.push(...data);
            }
          }, error1 => {
            console.log(error1);
          })
      } else if (this.showActiveFlag) {
        this.meetupService.getMeetupsChunkActive(this.lastRow, this.meetupsChunkSize).toPromise().then(
          data => {
            this.loading = false;
            if (data) {
              this.lastRow += data.length;
              this.meetupsList.push(...data);
              console.log(data);
              console.log(this.showActiveFlag);
              console.log(this.lastRow);
            }
          }, error1 => {
            console.log(error1);
          })
      } else if (this.showMyFlag) {
        this.meetupService.getUserMeetupsChunk(this.lastRow, this.meetupsChunkSize).toPromise().then(
          data => {
            this.loading = false;
            if (data) {
              this.lastRow += data.length;
              console.log(data);
              console.log(this.showMyFlag);
              console.log(this.lastRow);
              this.meetupsList.push(...data);
            }
          }, error1 => {
            console.log(error1);
          })
      }
    }
  }

  showActive() {
    this.showActiveFlag = true;
    this.showAllFlag = false;
    this.showMyFlag = false;
    this.meetupsList = [];
    this.lastRow = 0;
    this.loadMeetupsChunk();
  }

  showAll() {
    this.showActiveFlag = false;
    this.showAllFlag = true;
    this.showMyFlag = false;
    this.meetupsList = [];
    this.lastRow = 0;
    this.loadMeetupsChunk();
  }

  showMy() {
    this.showActiveFlag = false;
    this.showAllFlag = false;
    this.showMyFlag = true;
    this.meetupsList = [];
    this.lastRow = 0;
    this.loadMeetupsChunk();
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
