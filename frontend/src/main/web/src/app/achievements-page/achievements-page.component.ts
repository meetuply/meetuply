import {Component, Input, OnInit} from '@angular/core';
import {Achievement} from "../_models/achievement";
import {AchievementService} from "../_services/achievement.service";
import { Location } from '@angular/common';

@Component({
  selector: 'app-achievements-page',
  templateUrl: './achievements-page.component.html',
  styleUrls: ['./achievements-page.component.less']
})
export class AchievementsPageComponent implements OnInit {

  achievements: Achievement[];

  constructor(private achievementService: AchievementService, private location: Location) { }

  ngOnInit() {
    this.loadAchievements();
  }

  loadAchievements(){
    this.achievementService.getAll().toPromise().then(
      data => {
        console.log(data[0]);
        this.achievements = data;
      }, error => {
        console.log(error);
      }
    );
  }

  achievementToggled(uid) {
    this.achievementService.deleteFromAchievement(uid).subscribe(
      data=>{
        if (!data){
          console.log("success");
          const index = this.achievements.indexOf(this.achievements.find(
            x => x.achievementId), 0);
          if (index > -1) {
            this.achievements.splice(index, 1);
          }
        }
      }, error => {
        console.log(error);
      }
    );
  }

  public goBack(){
    this.location.back();
  }
}
