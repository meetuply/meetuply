import { Component, OnInit } from '@angular/core';
import {User} from "../_models";
import {AuthenticationService, UserService} from "../_services";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  registered = false;
  error = null;
  loading = false;

  constructor(
    private userService: UserService,
    private router: Router,
    private authService: AuthenticationService) { }

  ngOnInit() {
  }

  register() {
    this.loading = true;
    this.userService.register(this.user).subscribe(
      data => {
          if (data == null) {
            this.registered = true;
            //this.router.navigate(["/speakers"]);
            this.loading = false;
          }
      }, error => {
        this.error = error;
        this.loading = false;
      });
  }

}
