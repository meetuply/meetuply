import { Component, OnInit } from '@angular/core';
import {BanReason} from "../_models/banReason";
import {BanReasonService} from "../_services/ban-reason.service";
import {BanService} from "../_services/ban.service";
import {Ban} from "../_models/ban";
import {ActivatedRoute } from "@angular/router";
import {UserService} from "../_services";
import { Location } from '@angular/common';

@Component({
  selector: 'app-ban-page',
  templateUrl: './ban-page.component.html',
  styleUrls: ['./ban-page.component.less']
})
export class BanPageComponent implements OnInit {

  reportedId: number;
  banReasonId: number;
  banReasons: BanReason[];
  userService: UserService;

  getBanReasons(): void {
    this.banReasonService.getAll().subscribe(banReasons => this.banReasons = banReasons);
  }

  addBanDescription(description: string): void {
    description = description.trim();
    if (!description) { return; }
    this.banService.createBan({ description } as Ban, this.reportedId, this.banReasonId)
      .subscribe();
  }

  addBanReason(banReason: BanReason): void {
    this.banReasonId = banReason.banReasonId;
  }

  constructor(private banService: BanService, private banReasonService: BanReasonService,
              private route: ActivatedRoute, private location: Location) { }

  ngOnInit() {
    this.reportedId = this.route.snapshot.params['id'];
    this.getBanReasons();
  }

  public goBack(){
    this.location.back();
  }

}
