import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import {BanReason} from "../_models/banReason";
import {BanReasonService} from "../_services/ban-reason.service";
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-ban-reason-search',
  templateUrl: './ban-reason-search.component.html',
  styleUrls: ['./ban-reason-search.component.less']
})
export class BanReasonSearchComponent implements OnInit {

  banReasons$: Observable<BanReason[]>;
  private searchTerms = new Subject<string>();

  constructor(private banReasonService: BanReasonService) { }

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit() {
    this.banReasons$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.banReasonService.searchBanReasons(term)),
    );
  }

}
