import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent implements OnInit {

  @Input() text: string;
  @Input() fontSize = 20;
  @Input() href: string;
  @Input() type = 1;
  @Input() onClickp: any;
  constructor() { }

  ngOnInit() {
  }

  getClass() {
    return 'button btn' + this.type;
  }

}
