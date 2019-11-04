import { Component, OnInit, Input } from '@angular/core';
import {Topic} from "../_models";
import {TopicService} from "../_services";
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-topic-page',
  templateUrl: './topic-page.component.html',
  styleUrls: ['./topic-page.component.less']
})
export class TopicPageComponent implements OnInit {

  @Input() topic: Topic;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private topicService: TopicService
  ) { }

  getTopic(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.topicService.getTopic(id)
      .subscribe(topic => this.topic = topic);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.topicService.updateTopic(this.topic)
      .subscribe(() => this.goBack());
  }

  ngOnInit() {
    this.getTopic();
  }

}
