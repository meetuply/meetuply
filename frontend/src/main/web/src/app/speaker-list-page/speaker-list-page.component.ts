import {Component, OnInit} from '@angular/core';
import {Speaker_list_item} from '../_models/speaker_list_item';
import {HttpClient} from "@angular/common/http";
import {UserService} from "../_services/user.service"
import {User} from "../_models";

@Component({
  selector: 'app-speaker-list-page',
  templateUrl: './speaker-list-page.component.html',
  styleUrls: ['./speaker-list-page.component.css', '../fonts.css']
})
export class SpeakerListPageComponent implements OnInit {


  loading = false;
  chunkSize = 5;
  //maxMeetupsOnPage: number;
  scrollDistance = 2;

  speaker_list: Speaker_list_item[] = [];
  speaker_chunk: Speaker_list_item[];

  constructor(private http: HttpClient, private userService: UserService) {
  }

  isOdd(num: number): boolean {
    return num % 2 == 0;
  }

  onScrollDown() {
    console.log(this.speaker_list.length);
    this.loadUsersChunk();
  }

  loadUsersChunk() {
    this.userService.getChunk(this.speaker_list.length, this.chunkSize).subscribe(
      async users => {
        this.speaker_chunk = await Promise.all(users.map(async user => {
          let user_languages: string[];
          let list_item: Speaker_list_item;
          await this.userService.getUserLanguages(user.userId).toPromise().then(languages =>
            user_languages = languages.map(language => language.name)
          );

          let followers: number[];
          let follow: boolean;
          await this.userService.getUserFollowers(user.userId).toPromise().then(f =>
            followers = f
          );

          list_item = {
            id: user.userId,
            name: user.firstName,
            surname: user.lastName,
            location: user.location,
            rate: 4,
            description: user.description,
            languages: user_languages,
            following: (followers.indexOf(this.userService.currentUser.userId) != -1),
            awards: 3
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
