import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {User} from "./_models";
import {AuthenticationService} from "./_services";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css','./fonts.css']
})
export class AppComponent {
  title = 'meetuply';
  currentUser: User;

  constructor( private http: HttpClient, private router: Router, private authenticationService:AuthenticationService) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

}
