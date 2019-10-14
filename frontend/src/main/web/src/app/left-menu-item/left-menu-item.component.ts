import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {Menu_item} from "../menu_item";

@Component({
  selector: 'app-left-menu-item',
  templateUrl: './left-menu-item.component.html',
  styleUrls: ['./left-menu-item.component.css'],
  outputs: ['clck', 'action']
})
export class LeftMenuItemComponent implements OnInit {

  @Input() item: Menu_item;
  @Input() sel: string;
  @Output() clck = new EventEmitter<string>();

  bg: string;

  constructor() {}

  onClick() {
    if (this.item.action) this.item.action();

    this.clck.emit(
      this.item.text
    )
  }

  setClasses() {
    return {
      'left_menu_item': true,
      'selected' : this.sel == this.item.text
    }
  }

  ngOnInit() {
    this.bg = "url(/assets/icons/" + this.item.icon + ")";
  }

}
