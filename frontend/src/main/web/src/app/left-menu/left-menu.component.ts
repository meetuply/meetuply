import { Component, OnInit, Output } from '@angular/core';
import { MenuItem } from '../_models/menuItem';
import { HttpClient } from "@angular/common/http";
import { Router, ActivatedRoute } from "@angular/router";
import { AuthenticationService, UserService } from "../_services";
import {User} from "../_models";


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.css']
})


export class LeftMenuComponent implements OnInit {

  selectedItem: string;
  user: User;

  menu_items: MenuItem[] = [
    { icon: "apps.png", text: 'dashboard', redirectTo: "dashboard", userCanSee: true, adminCanSee: false},
    { icon: "globe.png", text: 'meetups', redirectTo: "meetups", userCanSee: true, adminCanSee: true},
    { icon: "user.png", text: 'speakers', redirectTo: "speakers", userCanSee: true, adminCanSee: false },
    { icon: "user.png", text: 'users', redirectTo: "deactivation", userCanSee: false, adminCanSee: true },
    { icon: "comment.png", text: 'chat', redirectTo: "chats", userCanSee: true, adminCanSee: false },
    { icon: "calendar.png", text: 'blog', redirectTo: "blog", userCanSee: true, adminCanSee: true },
    { icon: "bell.png", text: 'notifications', redirectTo: "notifications", userCanSee: true, adminCanSee: false },
    { icon: "star.png", text: 'achievements', redirectTo: "achievements", userCanSee: false, adminCanSee: true },
    { icon: "comment.png", text: 'bans', redirectTo: "ban_reasons", userCanSee: false, adminCanSee: true },
    { icon: "apps.png", text: 'topics', redirectTo: "topics", userCanSee: false, adminCanSee: true },
  ];


  bottom_menu_items: MenuItem[] = [
    { icon: "settings.svg", text: 'settings', redirectTo: "settings", userCanSee: true, adminCanSee: true },
    { icon: "turn-off.svg", text: 'log out', redirectTo: null, userCanSee: true, adminCanSee: true }
  ];

  constructor(private http: HttpClient,
    private router: Router,
    private authenticationService: AuthenticationService,
    public userService: UserService,
    private route: ActivatedRoute
  ) { }

  childClicked($event) {
    this.selectedItem = $event;
    this.router.navigateByUrl($event);
  }

  bottomMenuClicked($event) {
    if ($event == "settings") {

    } else if ($event == "log out") {
      this.authenticationService.logout().subscribe(data => this.router.navigate(['/login']));
    }
  }

  ngOnInit() {
    this.userService.getCurrentUser().subscribe(user => this.user = user);

    this.route.pathFromRoot[1].url.subscribe(val => {

      if (val[0].toString() == 'create' && val[1].toString() == 'meetup') {
        this.selectedItem = 'meetups';
      } else if (val[0].toString() == 'chats') {
        this.selectedItem = 'chat'
      }
      else {
        this.selectedItem = val[0].toString()
      }

    });

  }
}
