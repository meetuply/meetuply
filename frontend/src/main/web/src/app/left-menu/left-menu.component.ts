import {Component, OnInit, Output} from '@angular/core';
import {Menu_item} from '../menu_item';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.css'],
  outputs: ['selectedItem']
})


export class LeftMenuComponent implements OnInit {

  @Output() selectedItem = "meetups";

  menu_items: Menu_item[] = [
    {icon: "apps", text: 'dashboard', action: null},
    {icon: "globe", text: 'meetups', action: null},
    {icon: "user", text: 'speakers', action: null},
    {icon: "comment", text: 'chat', action: null},
    {icon: "calendar", text: 'blog', action: null},
    {icon: "bell", text: 'notifications', action: null}
  ];


  bottom_menu_items: Menu_item[] = [
    { icon: "apps", text: 'settings', action: null },
    { icon: "bell", text: 'log out', action: this.logout}
  ];

  constructor(private http: HttpClient, private router: Router) {}

  childClicked($event) {
    this.selectedItem = $event;
  }

  ngOnInit() {
  }

  logout() {
    console.log("logout");
    this.http.post('logout', {});
    this.router.navigate(["/"])
  }

}
