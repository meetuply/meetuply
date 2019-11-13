import { Component, OnInit } from '@angular/core';
import {Language, User} from "../_models";
import {LanguageService, UserService} from "../_services";
import {Router} from "@angular/router";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.less']
})
export class SettingsComponent implements OnInit {

  user = new User();
  languages: Language[];
  selectedLanguages = new Set<number>();
  error: string;

  constructor(private userService: UserService,
              private languageService: LanguageService,
              private router: Router) { }

  ngOnInit() {
    this.userService.getCurrentUser().subscribe(user => {
      this.user = user;
      user.password = null;
      this.loadLanguages();
    });
  }

  apply() {
    this.userService.update(this.user).subscribe(
      data => {
        this.languageService.updateUser(this.user.userId, Array.from(this.selectedLanguages))
          .subscribe(data => this.router.navigate(['speakers', this.user.userId])),
          error => this.error
      },
      error => this.error = error,
    )
  }

  langToggled($event) {
    console.log($event[0]);
    let id = parseInt($event[0]);
    console.log(id)
    if (this.selectedLanguages.has(id)) {
      this.selectedLanguages.delete(id)
    } else {
      this.selectedLanguages.add(id);
    }
    console.log(this.selectedLanguages)
  }

  loadLanguages() {
    this.languageService.getAll().subscribe(languageList => {
      this.languages = languageList;
      this.userService.getUserLanguages(this.user.userId).subscribe(
        data => {
          data.forEach(l => {
            this.selectedLanguages.add(l.languageId)

          });
        }
      );
    });
  }
}
