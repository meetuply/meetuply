import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import {BlogService} from "../_services/blog.service";
import {BlogPost} from "../_models";
import {UserService} from "../_services";

@Component({
  selector: 'app-blog-create-page',
  templateUrl: './blog-create-page.component.html',
  styleUrls: ['./blog-create-page.component.less']
})
export class BlogCreatePageComponent implements OnInit {

  constructor(private blogService: BlogService, private userService: UserService) { }

  post_title: string;
  post_content: string;

  submit($event) {
    var datetime = Date.now();

    var blogpost: BlogPost = {
      blogPostId: 0,
      blogPostTitle: this.post_title,
      blogPostContent: this.post_content,
      meetupFinishDateTime: end_date
    }

    this.meetupService.create(meetup).subscribe(data => {
      if (data == null) {
        alert("meetup created!")
        //supposed to redirect somewhere
      }
    }, error => {
      alert(error)
    });


  }

  loadLanguages() {
    this.languageService.getAll().subscribe(languageList => {
      this.languages = languageList.map(language => language.name)
      if (!this.languages || !this.languages.length) {
        this.languages = [
          'English',
          'Spanish',
          'German'
        ]
      }
    })
  }

  ngOnInit() {
    this.loadLanguages();
    this.loadTopics();

  }


  goBack() {
    this._location.back();
  }

}
