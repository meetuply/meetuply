import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-left-menu-item',
  templateUrl: './left-menu-item.component.html',
  styleUrls: ['./left-menu-item.component.css'],
  outputs: ['clck']
})
export class LeftMenuItemComponent implements OnInit {

  @Input() text: string;
  @Input() icon: string;
  @Input() sel: string;
  @Output() clck = new EventEmitter<string>();

  bg: string;

  constructor() {


  }

  onClick() {

    this.clck.emit(
      this.text
    )
  }

  setClasses() {

    return {
      'left_menu_item': true,
      'selected' : this.sel == this.text
    }
  }

  ngOnInit() {

    this.bg = "url(/assets/icons/" + this.icon + ".png)";

  }

}
