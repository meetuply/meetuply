import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {

  @Input() rating: number;

  @Input() size: number;
  constructor() { }

  ngOnInit() {

  }

  num(): Array<number> {
    let v = new Array();
    for (let i = 0; i < Math.floor(this.rating); i++) {
      v.push(i);
    }
    return v;
  }

}
