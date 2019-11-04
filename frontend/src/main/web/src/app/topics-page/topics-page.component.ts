import { Component, OnInit } from '@angular/core';

// @ts-ignore
import { Topic } from "../_models";
import { TopicService } from "../_services";

@Component({
  selector: 'app-topics-page',
  templateUrl: './topics-page.component.html',
  styleUrls: ['./topics-page.component.less']
})
export class TopicsPageComponent implements OnInit {
  topics: Topic[];

  getTopics(): void {
    this.topicService.getAll().subscribe(topics => this.topics = topics);
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.topicService.createTopic({ name } as Topic)
      .subscribe(topics => {
        this.getTopics();
      });
  }

  delete(topic: Topic): void {
    this.topics = this.topics.filter(h => h !== topic);
    this.topicService.deleteTopic(topic).subscribe();
  }

  constructor(private topicService: TopicService) { }

  ngOnInit() {
    this.getTopics();
  }

}
