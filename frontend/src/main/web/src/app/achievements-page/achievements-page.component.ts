import {Component, Input, OnInit} from '@angular/core';
import {Achievement} from "../_models/achievement";
import {AchievementService} from "../_services/achievement.service";
import { Location } from '@angular/common';
import {TopicService} from "../_services";

@Component({
  selector: 'app-achievements-page',
  templateUrl: './achievements-page.component.html',
  styleUrls: ['./achievements-page.component.less']
})
export class AchievementsPageComponent implements OnInit {

  achievements: Achievement[];
  iconSrc = "";
  iconExists = true;

  constructor(private achievementService: AchievementService, private location: Location,
              private topicService: TopicService) { }

  ngOnInit() {
    this.loadAchievements();
  }

  achievementToggled(uid) {
    this.achievementService.deleteFromAchievement(uid).subscribe(
      data=>{
        if (!data){
          this.achievements.splice(this.achievements.findIndex(function(i){
            return i.achievementId === uid;
          }), 1);
        }
      }, error => {
        console.log(error);
      }
    );
  }

  async loadAchievements(){
    await this.achievementService.getAll().subscribe(
        data => {
         this.achievements = data;
         this.achievements.forEach(a => this.topicService.getAchievementTopics(a.achievementId)
           .subscribe(data => {
             a.topics = data;
             if (a.topics.length > 0){
               this.topicService.getTopicQuantity(a.topics[0].topicId).subscribe(
                 data => a.meetups = data
               )
             }
           }));
       }, error => {
        console.log(error);
      }
    );
  }

  // checkImageExists(imageUrl, callBack) {
  //   var imageData = new Image();
  //   imageData.onload = function() {
  //     callBack(true);
  //   };
  //   imageData.onerror = function() {
  //     callBack(false);
  //   };
  //   imageData.src = imageUrl;
  // }
  //
  // check(imageUrl){
  //   this.checkImageExists(imageUrl, function(exists){
  //     if (exists){
  //       this.iconSrc = imageUrl;
  //     } else {
  //       this.iconSrc = "";
  //     }
  //   });
  // }

  public goBack(){
    this.location.back();
  }
}
