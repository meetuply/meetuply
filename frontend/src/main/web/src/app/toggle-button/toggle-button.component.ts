import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-toggle-button',
  templateUrl: './toggle-button.component.html',
  styleUrls: ['./toggle-button.component.less'],
  outputs: ['toggled']
})
export class ToggleButtonComponent implements OnInit {

  @Input() id: number;
  @Input() text: string;
  @Input() active = false;

  @Input() icon: string;

  @Output() toggled = new EventEmitter<[number, boolean]>();

  constructor() { }

  ngOnInit() {

  }

  getClass() {
    return 'toggle_button' + (this.active ? ' active' : '');
  }

  bg() {
    return {
      "background-image": "url(/assets/icons/" + this.icon + ".png)",
      "background-size": "cover"
    };
  }

  toggle() {
    this.active = !this.active;
    this.toggled.emit([this.id, this.active]);
  }

}
