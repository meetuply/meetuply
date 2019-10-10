import { NgModule }              from '@angular/core';
import { RouterModule, Routes }  from '@angular/router';
import {LandingPageComponent} from "./landing-page/landing-page.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {SpeakerPageComponent} from "./speaker-page/speaker-page.component";

const routes: Routes = [
  { path: '', component: LandingPageComponent},
  { path: 'login', component: LoginComponent},
  { path: 'registration', component: RegisterComponent},
  { path: 'speaker', component: SpeakerPageComponent},
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes,
      {useHash: true}
    )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
