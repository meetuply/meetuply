import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Achievement} from "../_models/achievement";
import {AchievementService} from "../_services/achievement.service";
import {TopicService} from "../_services";

@Component({
  selector: 'app-achievement-create-page',
  templateUrl: './achievement-create-page.component.html',
  styleUrls: ['./achievement-create-page.component.less']
})
export class AchievementCreatePageComponent implements OnInit {

  //todo update/create validation, allowCount

  achievementForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      icon: ['', Validators.required],
      followers: ['', Validators.required],
      posts: ['', Validators.required],
      rating: ['', Validators.required],
      meetups: ['', Validators.required],
      topics: new FormArray([])
    }
  );
  topics: any;
  showTopics: boolean = false;
  selectedOption = 'followers';

  constructor(private fb: FormBuilder, private achievementService: AchievementService,
              private topicService: TopicService) {
  }

  ngOnInit() {
    this.loadTopics();
    this.selectedOption = 'followers';
  }

  submit(event) {
    // let achievement: Achievement = new Achievement();
    // achievement.title = this.achievementForm['title'].value;
    // achievement.description = this.achievementForm['description'].value;
    // achievement.icon = this.achievementForm['icon'].value;
    // achievement.followers_number = this.achievementForm['followers_number'].value;
    // achievement.posts_number = this.achievementForm['posts_number'].value;
    // this.achievementService.create(achievement).subscribe(
    //   data => {
    //     if (!data) {
    //       alert("Achievement successfully created!");
    //     }
    //   }, error => {
    //     console.log(error)
    //   }
    // )
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

  onCheckChange(event){
    const formArray: FormArray = this.achievementForm.get('topics') as FormArray;
    /* Selected */
    if(event.target.checked){
      // Add a new control in the arrayForm
      formArray.push(new FormControl(event.target.value));
    }
    /* unselected */
    else{
      // find the unselected element
      let i: number = 0;
      formArray.controls.forEach((ctrl: FormControl) => {
        if(ctrl.value == event.target.value) {
          // Remove the unselected element from the arrayForm
          formArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }
}

// const formArray: FormArray = this.achievementForm.get('topics') as FormArray;
// console.log("removed"+formArray.controls.forEach((ctrl: FormControl)=>{
//   console.log(" Item: "+ ctrl.value)
// }))
