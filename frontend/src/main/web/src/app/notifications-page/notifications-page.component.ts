import { Component, OnInit, HostListener } from '@angular/core';
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
  lastRow = 0;
  notificationChunkSize = 15;
  loadedNotifications: Notification[] = []

  height: number;


  readAll() {
    this.userService.readAll(this.userService.currentUser.userId).subscribe(
      d => {
        this.loadedNotifications = [];
        this.lastRow = 0;
      },error => {
        console.log("error happened");
      }
    )


  }


  onScroll($event) {
    var scroll = $event.srcElement.scrollTop - $event.srcElement.clientHeight;
    if (scroll <= 10) {
      this.loadNotificationsChunk();

    }

  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.height = window.innerHeight;
  }

  setStyle() {
    return {
      'height': this.height + "px"
    }
  }


  loadNotificationsChunk() {

    if (this.active === 0) {


      this.userService.getUnreadNotificationsChunk(this.userService.currentUser.userId, this.lastRow, this.notificationChunkSize).subscribe(
        notifications => {
          if (notifications && notifications.length > 0) {
            this.loadedNotifications.push(...notifications);
            this.lastRow += notifications.length;
          }
        }, error => {
          console.log("failed to load chunk")
        }
      );

    } else if (this.active === 1) {

      this.userService.getNotificationsChunk(this.userService.currentUser.userId, this.lastRow, this.notificationChunkSize).subscribe(
        notifications => {
          if (notifications && notifications.length > 0) {
            this.loadedNotifications.push(...notifications);
            this.lastRow += notifications.length;
          }
        }, error => {
          console.log("failed to load chunk")
        }
      );


    } else if (this.active === 2) {


      this.userService.getReadNotificationsChunk(this.userService.currentUser.userId, this.lastRow, this.notificationChunkSize).subscribe(
        notifications => {
          if (notifications && notifications.length > 0) {
            this.loadedNotifications.push(...notifications);
            this.lastRow += notifications.length;
          }
        }, error => {
          console.log("failed to load chunk")
        }
      );

    }
  }

  ngOnInit() {
    this.height = window.innerHeight;
    this.loadNotificationsChunk();
    this.subscribeForNewNotifications();
  }


  subscribeForNewNotifications() {
    this.notificationService.connect(this.userService.currentUser.userId, data => {
      let msg = JSON.parse(data.body);
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
    this.lastRow = 0;
    this.active = 0;
    this.loadedNotifications = []
    this.loadNotificationsChunk();
  }

  allClick() {
    this.lastRow = 0;
    this.active = 1;
    this.loadedNotifications = []
    this.loadNotificationsChunk();
  }

  oldClick() {
    this.lastRow = 0;
    this.active = 2;
    this.loadedNotifications = []
    this.loadNotificationsChunk();
  }


}
