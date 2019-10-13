import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css','./fonts.css']
})
export class AppComponent {
  title = 'meetuply';

  constructor( private http: HttpClient, private router: Router) {
  }

}
