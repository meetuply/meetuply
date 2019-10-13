import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../_services";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loading = false;
  error = '';

  credentials = {username: '', password: ''};

  constructor( private router: Router,
               private authenticationService: AuthenticationService) {
  }

  login() {
    this.authenticationService.login(this.credentials.username, this.credentials.password)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate(["/speakers"]);
        },
        error => {
          this.error = error;
          this.loading = false;
        });
  }
}
