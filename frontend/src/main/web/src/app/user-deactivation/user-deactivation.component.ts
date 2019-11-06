import { Component, OnInit } from '@angular/core';
import {Speaker_list_item} from "../_models/speaker_list_item";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../_services";
import {RatingService} from "../_services/rating.service";

@Component({
  selector: 'app-user-deactivation',
  templateUrl: './user-deactivation.component.html',
  styleUrls: ['./user-deactivation.component.less']
})
export class UserDeactivationComponent implements OnInit {


  loading = false;
  chunkSize = 5;
  scrollDistance = 2;

  speaker_list: Speaker_list_item[] = [];
  speaker_chunk: Speaker_list_item[];

  isOdd(num: number): boolean {
    return num % 2 == 0;
  }

  onScrollDown() {
    console.log(this.speaker_list.length);
    this.loadUsersChunk();
  }

  constructor(private http: HttpClient, private userService: UserService, private ratingService: RatingService) { }

  loadUsersChunk() {
    this.userService.getChunkForAdmin(this.speaker_list.length, this.chunkSize).subscribe(
      async users => {
        this.speaker_chunk = await Promise.all(users.map(async user => {
          let user_languages: string[];
          let list_item: Speaker_list_item;
          await this.userService.getUserLanguages(user.userId).toPromise().then(languages =>
            user_languages = languages.map(language => language.name)
          );

          let rating = this.ratingService.getUserRatingAvg(user.userId).toPromise().then(r => rating = r);

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
            awards: 3
          };


          return list_item;

        }))
        this.speaker_list.push(...this.speaker_chunk);

      }
    )
  }

  ngOnInit() {

    this.loadUsersChunk();

  }
}
