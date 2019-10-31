import {Component, Input, OnInit} from '@angular/core';
import {Achievement} from "../_models/achievement";
import {AchievementService} from "../_services/achievement.service";

@Component({
  selector: 'app-achievements-page',
  templateUrl: './achievements-page.component.html',
  styleUrls: ['./achievements-page.component.less']
})
export class AchievementsPageComponent implements OnInit {

  achievements: Achievement[];

  constructor(private achievementService: AchievementService) { }

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
          location.reload();
        }
      }, error => {
        console.log(error);
      }
    );
  }
}
