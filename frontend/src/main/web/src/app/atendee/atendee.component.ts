import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-atendee',
  templateUrl: './atendee.component.html',
  styleUrls: ['./atendee.component.less']
})
export class AtendeeComponent implements OnInit {

  @Input() name: string;
  @Input() surname: string;
  @Input() rating: number;
  @Input() photo: string;

  constructor() { }

  ngOnInit() {
  }

}
