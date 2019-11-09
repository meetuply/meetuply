import {Component, OnInit, Input} from '@angular/core';
import {FeedbackListItem} from "../_models/feedback-list-item";

@Component({
  selector: 'app-feedback-list-item',
  templateUrl: './feedback-list-item.component.html',
  styleUrls: ['./feedback-list-item.component.css']
})
export class FeedbackListItemComponent implements OnInit {

  @Input() feedbackListItem: FeedbackListItem;
  error;

  constructor() {
  }

  ngOnInit() {
  }

}
