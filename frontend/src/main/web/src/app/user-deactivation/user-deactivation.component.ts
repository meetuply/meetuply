import { Component, OnInit } from '@angular/core';
import {Speaker_list_item} from "../speaker_list_item";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../_services";

@Component({
  selector: 'app-user-deactivation',
  templateUrl: './user-deactivation.component.html',
  styleUrls: ['./user-deactivation.component.less']
})
export class UserDeactivationComponent implements OnInit {


  loading = false;
  chunkSize = 5;
  //maxMeetupsOnPage: number;
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


  constructor(private http: HttpClient, private userService: UserService) { }

  loadUsersChunk() {
    this.userService.getChunk(this.speaker_list.length,this.chunkSize).subscribe(
      async users => {
        this.speaker_chunk = await Promise.all(users.map(async user => {

          var user_languages: string[];

          var list_item: Speaker_list_item;
          await this.userService.getUserLanguages(user.userId).toPromise().then(languages =>


            user_languages = languages.map(language => language.name)

          );


          list_item = {
            id: user.userId,
            name: user.firstName,
            surname: user.lastName,
            location: user.location,
            rate: 4,
            description: user.description,
            languages: user_languages,
            following: false,
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