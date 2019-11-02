import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Achievement} from "../_models/achievement";
import {AchievementService} from "../_services/achievement.service";
import {TopicService} from "../_services";

@Component({
  selector: 'app-achievement-create-page',
  templateUrl: './achievement-create-page.component.html',
  styleUrls: ['./achievement-create-page.component.less']
})
export class AchievementCreatePageComponent implements OnInit {

  //todo update validation

  achievementForm: FormGroup;
  topics: any;
  showTopics: boolean = false;
  selectedOption = 'followers';
  selectedTopics = new Set();

  constructor(private fb: FormBuilder, private achievementService: AchievementService,
              private topicService: TopicService) {
  }

  ngOnInit() {
    this.loadTopics();
    this.selectedOption = 'followers';
    this.achievementForm = this.fb.group({
        title: ['', Validators.required],
        description: ['', Validators.required],
        icon: ['', Validators.required],
        followers: ['', Validators.required],
        posts: ['', Validators.required],
        rating: ['', Validators.required],
        meetups: ['', Validators.required]
      }
    );
  }

  onSubmit(event) {
    // const topicsFormArray: FormArray = this.achievementForm.get('topics') as FormArray;
    // const topicsValues = topicsFormArray.value;
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
    } else if (!this.selectedTopics) {
      achievement.meetups = this.achievementForm.get('meetups').value;
    }
    this.achievementService.create(achievement).subscribe(
      achievementId => {
        if (this.selectedTopics) {
          console.log("New achievement Id " + achievementId);
          this.createForMeetupsSameQuantity(achievementId, Array.from(this.selectedTopics.values()));
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
        if (data) {
          console.log(data);
        }
      }, error => {
        console.log(error);
      }
    );
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

  goBack() {

  }
}
