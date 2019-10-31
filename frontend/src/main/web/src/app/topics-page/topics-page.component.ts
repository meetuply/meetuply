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
    this.topicService.getAll()
      .subscribe(topics => this.topics = topics);
  }

  add(name: string): void {
    //delete
    this.topics.push({ name } as Topic);
    //delete

    name = name.trim();
    if (!name) { return; }
    this.topicService.createTopic({ name } as Topic)
      .subscribe(topic => {
        this.topics.push(topic);
      });
  }

  constructor(private topicService: TopicService) { }

  ngOnInit() {
    this.getTopics();

    //delete
    this.topics = [
      { id: 11, name: 'Test Topic 1' },
      { id: 12, name: 'Test Topic 2' },
      { id: 13, name: 'Test Topic 3' }
    ];
    //delete
  }

}
