import {Component, OnInit} from '@angular/core';
import {SpeakerListItem} from '../_models/speakerListItem';
import {HttpClient} from "@angular/common/http";
import {UserService} from "../_services"
import {RatingService} from "../_services/rating.service";
import {AchievementService} from "../_services/achievement.service";

@Component({
  selector: 'app-speaker-list-page',
  templateUrl: './speaker-list-page.component.html',
  styleUrls: ['./speaker-list-page.component.css', '../fonts.css']
})
export class SpeakerListPageComponent implements OnInit {

  loading = false;
  chunkSize = 10;
  scrollDistance = 2;

  speaker_list: SpeakerListItem[] = [];
  speakerChunk: SpeakerListItem[];
  searchText: string="";
  searching=false;

  constructor(private http: HttpClient, private userService: UserService,
              private ratingService: RatingService,
              private achievementService: AchievementService) {
  }

  isOdd(num: number): boolean {
    return num % 2 == 0;
  }

  onScrollDown() {
    // console.log(this.speaker_list.length);
    if (!this.searching)
      this.loadUsersChunk();
    else
      this.loadSearchedChunk();
  }

  loadUsersChunk() {
    this.userService.getChunk(this.speaker_list.length, this.chunkSize).subscribe(
      async users => {
        this.speakerChunk = await Promise.all(users.map(async user => {
          let user_languages: string[];
          let list_item: SpeakerListItem;
          await this.userService.getUserLanguages(user.userId).toPromise().then(languages =>
            user_languages = languages.map(language => language.name)
          );
          let rating = this.ratingService.getUserRatingAvg(user.userId).toPromise().then(r => rating = r);
          let awards = 0;
          await this.achievementService.getUserAchievementsNumber(user.userId).toPromise().then(
            a => awards = a);
          let followers: number[];
          await this.userService.getUserFollowers(user.userId).toPromise().then(f =>
            followers = f
          );
          list_item = {
            id: user.userId,
            name: user.firstName,
            surname: user.lastName,
            location: user.location,
            rate: rating,
            description: user.description,
            languages: user_languages,
            following: (followers.indexOf(this.userService.currentUser.userId) != -1),
            photo: user.photo,
            awards: awards
          };
          return list_item;
        }));
        this.speaker_list.push(...this.speakerChunk);
      }
    )
  }

  loadSearchedChunk(){
    this.userService.getChunkByName(this.speaker_list.length, this.chunkSize, this.searchText).subscribe(
      async users => {
        this.speakerChunk = await Promise.all(users.map(async user => {
          let user_languages: string[];
          let list_item: SpeakerListItem;
          await this.userService.getUserLanguages(user.userId).toPromise().then(languages =>
            user_languages = languages.map(language => language.name)
          );
          let rating = this.ratingService.getUserRatingAvg(user.userId).toPromise().then(r => rating = r);
          let awards = 0;
          await this.achievementService.getUserAchievementsNumber(user.userId).toPromise().then(
            a => awards = a);
          let followers: number[];
          await this.userService.getUserFollowers(user.userId).toPromise().then(f =>
            followers = f
          );
          list_item = {
            id: user.userId,
            name: user.firstName,
            surname: user.lastName,
            location: user.location,
            rate: rating,
            description: user.description,
            languages: user_languages,
            following: (followers.indexOf(this.userService.currentUser.userId) != -1),
            photo: user.photo,
            awards: awards
          };
          return list_item;
        }));
        this.speaker_list.push(...this.speakerChunk);
      }
    )
  }

  search(event) {
    if (this.searchText.length>0) {
      this.speaker_list=[];
      this.speakerChunk=[];
      this.searching=true;
      this.loadSearchedChunk();
    }
    else{
      this.speaker_list=[];
      this.speakerChunk=[];
      this.searching=false;
      this.loadUsersChunk();
    }
  }

  ngOnInit() {
    this.loadUsersChunk();
  }

}
