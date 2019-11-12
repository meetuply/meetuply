import { Component } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { AuthenticationService, UserService } from "../_services";
import { first } from "rxjs/operators";

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
  recovering = false;
  requestEmail: string;
  successful = false;
  errorRecover = null;

  constructor( private router: Router,
               private route: ActivatedRoute,
               private authenticationService: AuthenticationService,
               private userService: UserService) {
  }

  ngOnInit() {
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'];
    if (this.authenticationService.currentUserValue) {
      console.log("already authed");
      this.router.navigate([this.returnUrl ? this.returnUrl : "/meetups"]);
    }
  }

  login() {
    this.loading = true;
    this.error = null;

    this.authenticationService.login(this.credentials.username, this.credentials.password)
      .pipe(first())
      .subscribe(
        data => {
          console.log("navigate");
          this.loading = false;
          this.router.navigate([this.returnUrl ? this.returnUrl : "/meetups"]);
        },
        error => {
          this.error = error;
          this.loading = false;
        });
  }

  recover() {
    this.loading = true;
    this.userService.requestRecover(this.requestEmail).subscribe(
      data => {
        this.successful = true;
        this.loading = false;
      },
      error => {
        this.errorRecover = error;
        this.loading = false;
      }
    );
  }
}
