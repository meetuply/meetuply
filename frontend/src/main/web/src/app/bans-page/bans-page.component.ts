import { Component, OnInit } from '@angular/core';
import {BanForView} from "../_models/banForView";
import {BanService} from "../_services/ban.service";
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-bans-page',
  templateUrl: './bans-page.component.html',
  styleUrls: ['./bans-page.component.less']
})
export class BansPageComponent implements OnInit {

  reportedId: number;
  bans: BanForView[];

  getBans(reportedId: number): void {
    this.banService.get(reportedId).subscribe(bans => this.bans = bans);
  }

  constructor(private banService: BanService, private route: ActivatedRoute, private location: Location) { }

  ngOnInit() {
    this.reportedId = this.route.snapshot.params['id'];
    this.getBans(this.reportedId);
  }

  public goBack(){
    this.location.back();
  }

}
