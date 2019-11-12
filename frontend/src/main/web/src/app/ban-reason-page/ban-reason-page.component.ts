import { Component, OnInit, Input } from '@angular/core';
import {BanReasonService} from "../_services/ban-reason.service";
import { ActivatedRoute } from '@angular/router';
import {BanReason} from "../_models/banReason";
import { Location } from '@angular/common';

@Component({
  selector: 'app-ban-reason-page',
  templateUrl: './ban-reason-page.component.html',
  styleUrls: ['./ban-reason-page.component.less']
})
export class BanReasonPageComponent implements OnInit {

  @Input() banReason: BanReason;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private banReasonService: BanReasonService
  ) { }

  getBanReason(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.banReasonService.getBanReason(id)
      .subscribe(banReason => this.banReason = banReason);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.banReasonService.updateBanReason(this.banReason)
      .subscribe(() => this.goBack());
  }

  delete(banReason: BanReason): void {
    this.banReasonService.deleteBanReason(banReason).subscribe(() => this.goBack());
  }

  ngOnInit() {
    this.getBanReason();
  }

}
