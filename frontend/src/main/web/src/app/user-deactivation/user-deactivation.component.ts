import { Component, OnInit } from '@angular/core';
import {SpeakerListItem} from "../_models/speakerListItem";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../_services";
import {RatingService} from "../_services/rating.service";
import {AchievementService} from "../_services/achievement.service";

@Component({
  selector: 'app-user-deactivation',
  templateUrl: './user-deactivation.component.html',
  styleUrls: ['./user-deactivation.component.less']
})
export class UserDeactivationComponent implements OnInit {


  loading = false;
  chunkSize = 5;
  scrollDistance = 2;

  speaker_list: SpeakerListItem[] = [];
  speaker_chunk: SpeakerListItem[];

  isOdd(num: number): boolean {
    return num % 2 == 0;
  }

  onScrollDown() {
    console.log(this.speaker_list.length);
    this.loadUsersChunk();
  }

  constructor(private http: HttpClient, private userService: UserService,
              private ratingService: RatingService,
              private achievementService: AchievementService) { }

  loadUsersChunk() {
    this.userService.getChunkForAdmin(this.speaker_list.length, this.chunkSize).subscribe(
      async users => {
        this.speaker_chunk = await Promise.all(users.map(async user => {
          let user_languages: string[];
          let list_item: SpeakerListItem;
          await this.userService.getUserLanguages(user.userId).toPromise().then(languages =>
            user_languages = languages.map(language => language.name)
          );
          let rating = this.ratingService.getUserRatingAvg(user.userId).toPromise().then(r => rating = r);
          let awards;
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
            awards: awards
          };
          return list_item;
        }));
        this.speaker_list.push(...this.speaker_chunk);
      }
    )
  }

  ngOnInit() {

    this.loadUsersChunk();

  }
}
