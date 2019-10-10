import { Component, OnInit, Output } from '@angular/core';
import { Menu_item } from '../menu_item';


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.css'],
  outputs: ['selectedItem']
})


export class LeftMenuComponent implements OnInit {

  @Output() selectedItem = "meetups";

  menu_items: Menu_item[] = [
    { icon: "apps", text: 'meetups' },
    { icon: "bell", text: 'other' },
    { icon: "apps", text: 'meetups2' },
    { icon: "bell", text: 'other2' }
  ];

  bottom_menu_items: Menu_item[] = [
    { icon: "apps", text: 'meetups' },
    { icon: "bell", text: 'other' }
  ];

  constructor() {

  }

  childClicked($event) {
    this.selectedItem = $event;
  }

  ngOnInit() {
  }

}
