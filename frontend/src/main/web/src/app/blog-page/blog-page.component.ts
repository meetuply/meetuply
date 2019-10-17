import { Component, OnInit } from '@angular/core';
import { History } from '../history'
import { Feedback } from "../feedback"
import { Location } from '@angular/common';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-blog-page',
  templateUrl: './blog-page.component.html',
  styleUrls: ['./blog-page.component.css', '../fonts.css']
})
export class BlogPageComponent implements OnInit {


  name = "John"
  surname = "Dee"
  post_title = "FGhjkl"
  post_content="Mauris tincidunt lobortis nulla, a blandit nulla laoreet vitae. Maecenas eget orci laoreet, suscs"

  goBack() {
    this._location.back();
  }

  constructor(private _location: Location, private router: Router) {
  }

  ngOnInit() {
  }

}
