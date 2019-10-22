import { Component, OnInit, Input } from '@angular/core';

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
  constructor() { }

  ngOnInit() {
  }

  getClass() {
    return 'button btn' + this.type;
  }


  //add output when clicked
}
