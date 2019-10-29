import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-achievement-panel',
  templateUrl: './achievement-panel.component.html',
  styleUrls: ['./achievement-panel.component.less']
})
export class AchievementPanelComponent implements OnInit {

  showDescription: boolean = false;
  @Input() title: string;
  @Input() description: string;
  @Input() icon: string;

  constructor() { }

  ngOnInit() {
  }

  addDescription(){
    this.showDescription = !this.showDescription;
  }

}
