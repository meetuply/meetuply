import { Component, OnInit } from '@angular/core';
import {LeftMenuItemComponent} from "../left-menu-item.component";
import {AuthenticationService} from "../../_services";
import {Router} from "@angular/router";

@Component({
  selector: 'app-logout-menu-item',
  templateUrl: './logout-menu-item.component.html',
  styleUrls: ['../left-menu-item.component.css']
})
export class LogoutMenuItemComponent extends LeftMenuItemComponent {

  constructor(private authService: AuthenticationService, private router: Router) {
    super();
    this.item = { icon: "turn-off.svg", text: 'log out', redirectTo: null};
  }

  onClick() {
    super.onClick();
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
