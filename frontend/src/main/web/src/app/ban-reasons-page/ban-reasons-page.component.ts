import { Component, OnInit } from '@angular/core';
import {BanReason} from "../_models/banReason";
import {BanReasonService} from "../_services/ban-reason.service";

@Component({
  selector: 'app-ban-reasons-page',
  templateUrl: './ban-reasons-page.component.html',
  styleUrls: ['./ban-reasons-page.component.less']
})
export class BanReasonsPageComponent implements OnInit {

  banReasons: BanReason[];

  getBanReasons(): void {
    this.banReasonService.getAll().subscribe(banReasons => this.banReasons = banReasons);
  }

  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.banReasonService.createBanReason({ name } as BanReason)
      .subscribe(banReasons => {
        this.getBanReasons();
      });
  }

  delete(banReason: BanReason): void {
    this.banReasons = this.banReasons.filter(h => h !== banReason);
    this.banReasonService.deleteBanReason(banReason).subscribe();
  }

  constructor(private banReasonService: BanReasonService) { }

  ngOnInit() {
    this.getBanReasons();
  }

}
