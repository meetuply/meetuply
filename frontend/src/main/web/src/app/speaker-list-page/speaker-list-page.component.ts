import { Component, OnInit } from '@angular/core';
import { Speaker_list_item } from '../speaker_list_item';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-speaker-list-page',
  templateUrl: './speaker-list-page.component.html',
  styleUrls: ['./speaker-list-page.component.css', '../fonts.css']
})
export class SpeakerListPageComponent implements OnInit {


  speaker_list: Speaker_list_item[] = [
    {
      name: "Jared", surname: 'Sunn', location: "Barcelona, Spain", rate: 4.5, following: true, awards: 3, languages: ["English", "Spanish"
      ], description: "Aenean vel seferrgrgrgedrhdtfhedrhdtjrtjurna odio. Suspendisse libero arcu, posuere quis tincidunt eu, tincidunt eget dui. Integer vitae ipsum eu nulla convallis sagittis"
    },
    {
      name: "Jared2", surname: 'Sunn', location: "Barcelona, Spain", rate: 4.5, following: true, awards: 3, languages: ["English", "Spanish"
      ], description: "Aenean vel seferrgrgrgedrhdtfhedrhdtjrtjurna odio. Suspendisse libero arcu, posuere quis tincidunt eu, tincidunt eget dui. Integer vitae ipsum eu nulla convallis sagittis"
    },
    {
      name: "Jared3", surname: 'Sunn', location: "Barcelona, Spain", rate: 4.5, following: true, awards: 3, languages: ["English", "Spanish"
      ], description: "Aenean vel seferrgrgrgedrhdtfhedrhdtjrtjurna odio. Suspendisse libero arcu, posuere quis tincidunt eu, tincidunt eget dui. Integer vitae ipsum eu nulla convallis sagittis"
    },
    {
      name: "Jared4", surname: 'Sunn', location: "Bar, Spain", rate: 4.0, following: false, awards: 2, languages: ["English"]
      , description: "Hallo Aenean vel seferrgrgrgedrhdtfhedrhdtjrtjurna odio. Suspendisse libero arcu, posuere quis tincidunt eu, tincidunt eget dui. Integer vitae ipsum eu nulla convallis sagittis"
    }
  ];

  isOdd(num: number): boolean {
    return num % 2 == 0;
  }

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

}
