import {Component, OnInit, Output} from '@angular/core';
import {Menu_item} from '../menu_item';


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.css'],
  outputs: ['selectedItem']
})


export class LeftMenuComponent implements OnInit {

  @Output() selectedItem = "meetups";

  menu_items: Menu_item[] = [
    {icon: "apps", text: 'dashboard'},
    {icon: "globe", text: 'meetups'},
    {icon: "user", text: 'speakers'},
    {icon: "comment", text: 'chat'},
    {icon: "calendar", text: 'blog'},
    {icon: "bell", text: 'notifications'}
  ];


  bottom_menu_items: Menu_item[] = [
    { icon: "apps", text: 'settings' },
    { icon: "bell", text: 'log out' }
  ];

  constructor() {

  }

  childClicked($event) {
    this.selectedItem = $event;
  }

  ngOnInit() {
  }

}
