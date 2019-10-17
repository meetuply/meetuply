import { Component, OnInit } from '@angular/core';
import {UserService} from "../_services";
import {ActivatedRoute, Route, Router} from "@angular/router";

@Component({
  selector: 'app-reg-confirmation',
  templateUrl: './reg-confirmation.component.html',
  styleUrls: ['./reg-confirmation.component.less']
})
export class RegConfirmationComponent implements OnInit {

  sucsessful = true;
  loading = true;
  token = null;

  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.token = this.route.snapshot.queryParams['token'];
    if (this.token)
      this.userService.activate(this.token)
        .subscribe(
          data => {
            this.sucsessful = true;
            this.loading = false;
          },
          error => {
            this.sucsessful = false;
            this.loading = false
          });
    else this.sucsessful = false;
  }

}
