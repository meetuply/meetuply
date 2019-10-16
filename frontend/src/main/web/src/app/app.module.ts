import { BrowserModule } from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { ButtonComponent } from './button/button.component';
import { LeftMenuComponent } from './left-menu/left-menu.component';
import { LeftMenuItemComponent } from './left-menu-item/left-menu-item.component';
import { SpeakerPageComponent } from './speaker-page/speaker-page.component';
import { SpeakerListPageComponent } from './speaker-list-page/speaker-list-page.component';
import { SpeakerListItemComponent } from './speaker-list-item/speaker-list-item.component';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from "@angular/forms";
import { RatingComponent } from "./rating/rating.component";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {BasicAuthInterceptor, ErrorInterceptor, XhrInterceptor} from "./_helpers";
import { LogoutMenuItemComponent } from './left-menu-item/logout-menu-item/logout-menu-item.component';
import {MustMatchDirective} from "./_helpers/validator/must-match.directive";
import { ToggleButtonComponent } from './toggle-button/toggle-button.component';
import { AtendeeComponent } from './atendee/atendee.component';
import { MeetupFilterComponent } from './meetup-filter/meetup-filter.component';
import { MeetupListItemComponent } from './meetup-list-item/meetup-list-item.component';
import { MeetupsListPageComponent } from './meetups-list-page/meetups-list-page.component';
import { MeetupPageComponent } from './meetup-page/meetup-page.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    LandingPageComponent,
    ButtonComponent,
    LeftMenuComponent,
    LeftMenuItemComponent,
    SpeakerPageComponent,
    SpeakerListPageComponent,
    SpeakerListItemComponent,
    RatingComponent,
    LogoutMenuItemComponent,
    MustMatchDirective,
    ToggleButtonComponent,
    AtendeeComponent,
    MeetupFilterComponent,
    MeetupListItemComponent,
    MeetupsListPageComponent,
    MeetupPageComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }


