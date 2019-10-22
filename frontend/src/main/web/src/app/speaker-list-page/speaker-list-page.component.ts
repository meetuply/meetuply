import { Component, OnInit } from '@angular/core';
import { Speaker_list_item } from '../speaker_list_item';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { environment } from "../../environments/environment";
import { UserService } from "../_services/user.service"
import { flatMap } from 'rxjs/operators';
import { User, Language } from '../_models'

@Component({
  selector: 'app-speaker-list-page',
  templateUrl: './speaker-list-page.component.html',
  styleUrls: ['./speaker-list-page.component.css', '../fonts.css']
})
export class SpeakerListPageComponent implements OnInit {


  speaker_list: Speaker_list_item[] = [];

  isOdd(num: number): boolean {
    return num % 2 == 0;
  }

  constructor(private http: HttpClient, private userService: UserService) { }

  
  


  loadUsers() {
    this.userService.getAll().subscribe(
      async users => {
        this.speaker_list = await Promise.all( users.map(async user =>  {

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

      }
    )
  }

  ngOnInit() {

    this.loadUsers();

  }

}
