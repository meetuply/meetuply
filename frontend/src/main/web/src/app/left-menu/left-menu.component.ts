import {Component, OnInit, Output} from '@angular/core';
import {Menu_item} from '../menu_item';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthenticationService} from "../_services";


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.css'],
  outputs: ['selectedItem']
})


export class LeftMenuComponent implements OnInit {

  @Output() selectedItem = "meetups";

  menu_items: Menu_item[] = [
    {icon: "apps.png", text: 'dashboard', action: null},
    {icon: "globe.png", text: 'meetups', action: null},
    {icon: "user.png", text: 'speakers', action: null},
    {icon: "comment.png", text: 'chat', action: null},
    {icon: "calendar.png", text: 'blog', action: null},
    {icon: "bell.png", text: 'notifications', action: null}
  ];


  bottom_menu_items: Menu_item[] = [
    { icon: "settings.svg", text: 'settings', action: null },
    // { icon: "bell", text: 'log out', action: null}
  ];

  constructor(private http: HttpClient, private router: Router, private authenticationService: AuthenticationService) {}

  childClicked($event) {
    this.selectedItem = $event;
  }

  ngOnInit() {
  }

  logot() {
    this.authenticationService.logout();
  }
}
