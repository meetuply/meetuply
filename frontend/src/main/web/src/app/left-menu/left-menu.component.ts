import { Component, OnInit, Output } from '@angular/core';
import { Menu_item } from '../_models/menu_item';
import { HttpClient } from "@angular/common/http";
import { Router,ActivatedRoute } from "@angular/router";
import { AuthenticationService, UserService } from "../_services";


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.css']
})


export class LeftMenuComponent implements OnInit {

  selectedItem: string;

  menu_items: Menu_item[] = [
    { icon: "apps.png", text: 'dashboard', redirectTo: "dashboard" },
    { icon: "globe.png", text: 'meetups', redirectTo: "meetups" },
    { icon: "user.png", text: 'speakers', redirectTo: "speakers" },
    { icon: "comment.png", text: 'chat', redirectTo: "chat" },
    { icon: "calendar.png", text: 'blog', redirectTo: "blog" },
    { icon: "bell.png", text: 'notifications', redirectTo: "notifications" },
    { icon: "achievement.svg", text: 'achievements', redirectTo: "achievements" }
  ];


  bottom_menu_items: Menu_item[] = [
    { icon: "settings.svg", text: 'settings', redirectTo: null },
    { icon: "turn-off.svg", text: 'log out', redirectTo: null }
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
    this.route.pathFromRoot[1].url.subscribe(val =>  {

        if(val[0].toString() == 'create' && val[1].toString() == 'meetup') {
          this.selectedItem = 'meetups';
        } else {
          this.selectedItem = val[0].toString()
        }

      });

  }
}
