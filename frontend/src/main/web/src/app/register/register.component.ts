import { Component, OnInit } from '@angular/core';
import { User } from "../_models";
import { UserService } from "../_services";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  user: User = new User();
  registered = false;
  error: any;
  loading = false;

  constructor(private userService: UserService) { }

  register() {
    this.loading = true;
    this.userService.register(this.user).subscribe(
      _ => {
          this.registered = true;
          this.loading = false;
      }, error => {
        this.error = error;
        this.loading = false;
      });
  }

}
