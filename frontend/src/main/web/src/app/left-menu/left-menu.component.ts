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
    {icon: "apps.png", text: 'dashboard', redirectTo: "dashboard"},
    {icon: "globe.png", text: 'meetups', redirectTo: "meetups"},
    {icon: "user.png", text: 'speakers', redirectTo: "speakers"},
    {icon: "comment.png", text: 'chat', redirectTo: "chat"},
    {icon: "calendar.png", text: 'blog', redirectTo: "blog"},
    {icon: "bell.png", text: 'notifications', redirectTo: "notifications"}
  ];


  bottom_menu_items: Menu_item[] = [
    { icon: "settings.svg", text: 'settings', redirectTo: null }
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
