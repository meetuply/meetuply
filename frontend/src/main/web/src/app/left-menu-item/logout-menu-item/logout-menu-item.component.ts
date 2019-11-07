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

  constructor(private authService: AuthenticationService, public router: Router) {
    super(router, authService);
    this.item = { icon: "turn-off.svg", text: 'log out', redirectTo: null, userCanSee: true, adminCanSee: true};
  }

  onClick() {
    super.onClick();
    this.authService.logout().subscribe(data => this.router.navigate(['/login']));
  }

}
