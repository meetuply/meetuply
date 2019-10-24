import { Injectable, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from "./landing-page/landing-page.component";
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { SpeakerPageComponent } from "./speaker-page/speaker-page.component";
import { SpeakerListPageComponent } from "./speaker-list-page/speaker-list-page.component";
import { MeetupsListPageComponent } from "./meetups-list-page/meetups-list-page.component";
import { MeetupPageComponent } from "./meetup-page/meetup-page.component";
import { AuthGuard } from "./_helpers";
import { RegConfirmationComponent } from "./reg-confirmation/reg-confirmation.component";

import { CreateMeetupPageComponent } from "./create-meetup-page/create-meetup-page.component";

import {UserDeactivationComponent} from "./user-deactivation/user-deactivation.component";
import {AdminGuard} from "./_helpers/admin.guard";


const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegisterComponent },
  { path: 'speakers/:id', component: SpeakerPageComponent, canActivate: [AuthGuard] },
  { path: 'speakers', component: SpeakerListPageComponent, canActivate: [AuthGuard] },
  { path: 'meetups/create', component: CreateMeetupPageComponent, canActivate: [AuthGuard] },
  { path: 'meetups/:id', component: MeetupPageComponent, canActivate: [AuthGuard] },
  { path: 'meetups', component: MeetupsListPageComponent, canActivate: [AuthGuard] },
  { path: 'confirm', component: RegConfirmationComponent},

  { path: 'deactivation', component: UserDeactivationComponent, canActivate: [AdminGuard] },

];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes,
      { useHash: true }
    )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
