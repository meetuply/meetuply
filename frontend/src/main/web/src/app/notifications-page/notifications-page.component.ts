import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services';
import { NotificationService } from '../_services/notification.service';

@Component({
  selector: 'app-notifications-page',
  templateUrl: './notifications-page.component.html',
  styleUrls: ['./notifications-page.component.less']
})
export class NotificationsPageComponent implements OnInit {

  constructor(private userService: UserService, private notificationService: NotificationService) { }


  active = 0;

  loadedNotifications: Notification[] = []

  loadNotifications() {

    if (this.active === 0) {
      this.userService.getUnreadNotifications(this.userService.currentUser.userId).subscribe(
        notifications => {
          this.loadedNotifications = notifications
          //console.log('!')
        }
      )


    } else if (this.active === 1) {
      this.userService.getNotifications(this.userService.currentUser.userId).subscribe(
        notifications => {
          this.loadedNotifications = notifications
          //console.log('!')
        }
      )
    } else if (this.active === 2) {
      this.userService.getReadNotifications(this.userService.currentUser.userId).subscribe(
        notifications => {
          this.loadedNotifications = notifications
          //console.log('!')
        }
      )
    }

  }

  ngOnInit() {

    this.loadNotifications();
    this.notificationService.connect(this.userService.currentUser.userId, data => {

      let msg = JSON.parse(data.body);
      msg.plain_text = msg.plainText;
      this.loadedNotifications.unshift(msg)
     
    });
  }


  getNewClass() {
    return (this.active === 0) ? 'active' : 'inactive'
  }

  getAllClass() {
    return (this.active === 1) ? 'active' : 'inactive'
  }

  getOldClass() {
    return (this.active === 2) ? 'active' : 'inactive'
  }


  newClick() {
    this.active = 0;
    this.loadNotifications();
  }

  allClick() {
    this.active = 1;
    this.loadNotifications();
  }

  oldClick() {
    this.active = 2;
    this.loadNotifications();
  }


}
