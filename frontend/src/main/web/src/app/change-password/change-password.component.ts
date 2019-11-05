import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../_models";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.less']
})
export class ChangePasswordComponent implements OnInit {

  sucsessful= false;
  loading = true;
  token = null;
  newPassword: string;
  confirmedPassword: string;
  error: any;

  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.token = this.route.snapshot.queryParams['token'];
    this.loading = false;
  }

  submit() {
    if (this.token) {
      let user = new User();
      user.password = this.newPassword;
      this.userService.recover(user, this.token)
        .subscribe(
          data => {
            this.sucsessful = true;
            this.loading = false;
          },
          error => {
            this.sucsessful = false;
            this.loading = false;
            this.error = error;
          });
    } else {
      let user = this.userService.currentUser;
      if (user) {
        user.password = this.newPassword;
        this.userService.update(user).subscribe(
          data => this.router.navigate(['/settings']),
          error => {
            this.sucsessful = false;
            this.loading = false;
            this.error = error;
          }
        )
      } else {
        this.error = "Please, sign in to change password";

      }
    }

  }
}
