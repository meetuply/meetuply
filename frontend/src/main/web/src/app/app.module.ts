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


