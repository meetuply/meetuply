import { Component, OnInit, Output } from '@angular/core';
import { MenuItem } from '../_models/menuItem';
import { HttpClient } from "@angular/common/http";
import { Router, ActivatedRoute } from "@angular/router";
import { AuthenticationService, UserService } from "../_services";
import { NotificationService } from '../_services/notification.service'

@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.css']
})


export class LeftMenuComponent implements OnInit {

  selectedItem: string;

  menu_items: MenuItem[] = [
    { icon: "apps.png", text: 'dashboard', redirectTo: "dashboard" },
    { icon: "globe.png", text: 'meetups', redirectTo: "meetups" },
    { icon: "user.png", text: 'speakers', redirectTo: "speakers" },
    { icon: "comment.png", text: 'chat', redirectTo: "chats" },
    { icon: "calendar.png", text: 'blog', redirectTo: "blog" },
    { icon: "bell.png", text: 'notifications', redirectTo: "notifications" },
    { icon: "star.png", text: 'achievements', redirectTo: "achievements" }
  ];


  bottom_menu_items: MenuItem[] = [
    { icon: "settings.svg", text: 'settings', redirectTo: "settings" },
    { icon: "turn-off.svg", text: 'log out', redirectTo: null }
  ];


  tempNotifications: Notification[] = [
    //"one",
    //"another"
  ];

  constructor(private http: HttpClient,
    private router: Router,
    private authenticationService: AuthenticationService,
    private notificationService: NotificationService,
    public userService: UserService,
    private route: ActivatedRoute
  ) {

  }

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


    this.notificationService.connect(this.userService.currentUser.userId, data => {
      //alert("New notification!!Do what you want with it, I go to sleep");

      let msg =  JSON.parse(data.body);
      this.tempNotifications.push(msg);

      setTimeout(() => {
        const index = this.tempNotifications.indexOf(msg, 0);
        this.tempNotifications.splice(index, 1)
      }, 3000)

      console.log("new notification!")
      console.log(data)
    })

  }

  notificationClick(val: any) {
    //this.router.navigateByUrl("/notifications")
  }


}
