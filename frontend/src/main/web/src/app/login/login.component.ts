import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../_services";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loading = false;
  error = null;
  returnUrl: string;

  credentials = {username: '', password: ''};

  constructor( private router: Router,
               private route: ActivatedRoute,
               private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
    // get return url from route parameters or default to '/'
    // this.returnUrl = this.route.snapshot.queryParams['returnUrl'];
  }

  login() {
    this.loading = true;
    this.error = null;

    this.authenticationService.login(this.credentials.username, this.credentials.password)
      .pipe(first())
      .subscribe(
        data => {
          console.log("navigate");
          this.router.navigate([this.returnUrl ? this.returnUrl : "/speakers"]);
        },
        error => {
          this.error = error;
          this.loading = false;
          console.log("ERROR " + error)
        });
  }
}
