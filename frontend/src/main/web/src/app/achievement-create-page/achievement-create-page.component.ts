import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Achievement} from "../_models/achievement";
import {AchievementService} from "../_services/achievement.service";
import {TopicService} from "../_services";
import {Location} from '@angular/common';

@Component({
  selector: 'app-achievement-create-page',
  templateUrl: './achievement-create-page.component.html',
  styleUrls: ['./achievement-create-page.component.less']
})
export class AchievementCreatePageComponent implements OnInit {

  achievementForm: FormGroup;
  topics: any;
  showTopics: boolean = false;
  selectedOption = 'followers';
  selectedTopics = new Set();
  disabled = true;

  constructor(private fb: FormBuilder, private achievementService: AchievementService,
              private topicService: TopicService, private location: Location) {
  }

  ngOnInit() {
    this.loadTopics();
    this.achievementForm = this.fb.group({
        title: ['', [Validators.required, Validators.maxLength(20)]],
        description: ['', [Validators.required, Validators.maxLength(70)]],
        icon: ['', [Validators.required, Validators.maxLength(500)]],
        followers: ['', Validators.required],
        posts: ['', Validators.required],
        rating: ['', Validators.required],
        meetups: ['', Validators.required]
      }
    );
  }

  onSubmit(event) {
    console.log("clicked");
    let achievement: Achievement = new Achievement();
    achievement.title = this.achievementForm.get('title').value;
    achievement.description = this.achievementForm.get('description').value;
    achievement.icon = this.achievementForm.get('icon').value;
    if (this.selectedOption == 'followers'){
      achievement.followers = this.achievementForm.get('followers').value;
    } else if (this.selectedOption == 'posts') {
      achievement.posts = this.achievementForm.get('posts').value;
    } else if (this.selectedOption == 'rating') {
      achievement.rating = this.achievementForm.get('rating').value;
    } else if (this.selectedTopics.size == 0) {
      achievement.meetups = this.achievementForm.get('meetups').value;
    }
    this.achievementService.create(achievement).subscribe(
      achievement => {
        if (this.selectedTopics.size > 0) {
          this.createForMeetupsSameQuantity(achievement.achievementId, Array.from(this.selectedTopics.values()));
        } else{
          this.location.back();
        }
      }, error => {
        console.log(error)
      }
    );
  }

  createForMeetupsSameQuantity(id, topicsValues) {
    const formData = new FormData();
    formData.append("achievementId", id);
    formData.append("topics", topicsValues);
    formData.append("quantity", this.achievementForm.get('meetups').value);
    this.achievementService.createForMeetupsSameQuantity(formData).subscribe(
      data => {
          console.log(data);
          this.location.back();
      }, error => {
        console.log(error);
      }
    )
  }

  onChange(selectedValue) {
    if (selectedValue == 'meetups') {
      this.showTopics = true;
      this.selectedOption = selectedValue;
      console.log(this.selectedOption);
    } else {
      this.showTopics = false;
      this.selectedOption = selectedValue;
      console.log(this.selectedOption);
    }
  }

  loadTopics() {
    this.topicService.getAll().toPromise().then(
      topicsList => {
        this.topics = topicsList;
      }
    )
  }

  topicToggled($event) {
    if ($event[1] == true) {
      this.selectedTopics.add($event[0])
    } else {
      this.selectedTopics.delete($event[0]);
    }
    console.log(this.selectedTopics)
  }


  public goBack(){
    this.location.back();
  }
}
