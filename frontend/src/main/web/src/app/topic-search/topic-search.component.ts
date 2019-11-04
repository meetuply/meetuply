import { Component, OnInit } from '@angular/core';
import {TopicService} from "../_services";
import {Topic} from "../_models";
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-topic-search',
  templateUrl: './topic-search.component.html',
  styleUrls: ['./topic-search.component.less']
})
export class TopicSearchComponent implements OnInit {

  topics$: Observable<Topic[]>;
  private searchTerms = new Subject<string>();

  constructor(private topicService: TopicService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit() {
    this.topics$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.topicService.searchTopics(term)),
    );
  }

}
