import { NgModule }              from '@angular/core';
import { RouterModule, Routes }  from '@angular/router';
import {LandingPageComponent} from "./landing-page/landing-page.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {SpeakerPageComponent} from "./speaker-page/speaker-page.component";
import {SpeakerListPageComponent} from "./speaker-list-page/speaker-list-page.component";

const routes: Routes = [
  { path: '', component: LandingPageComponent},
  { path: 'login', component: LoginComponent},
  { path: 'registration', component: RegisterComponent},
  { path: 'speaker', component: SpeakerPageComponent},
  { path: 'speakers', component: SpeakerListPageComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes
    )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
