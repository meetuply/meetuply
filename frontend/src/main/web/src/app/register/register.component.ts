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

  constructor(
    private userService: UserService,
    private router: Router,
    private authService: AuthenticationService) { }

  ngOnInit() {
  }

  register() {
    this.userService.register(this.user).subscribe(
      error => {
          if (error) {
            console.log(error);
          } else {
            this.registered = true;
            //this.router.navigate(["/speakers"]);
          }
      });
  }

}
