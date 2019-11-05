import { BrowserModule } from '@angular/platform-browser';
import { Injectable, NgModule } from '@angular/core';
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

import { BlogPageComponent } from './blog-page/blog-page.component';
import { BlogListPageComponent } from './blog-list-page/blog-list-page.component';
import { BlogListItemComponent } from './blog-list-item/blog-list-item.component';
import { BlogCreatePageComponent } from "./blog-create-page/blog-create-page.component";
import { BlogCommentItemComponent } from "./blog-comment-item/blog-comment-item.component";

import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from "@angular/forms";
import { RatingComponent } from "./rating/rating.component";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { BasicAuthInterceptor, ErrorInterceptor, XhrInterceptor } from "./_helpers";
import { LogoutMenuItemComponent } from './left-menu-item/logout-menu-item/logout-menu-item.component';
import { MustMatchDirective } from "./_helpers/validator/must-match.directive";
import { ToggleButtonComponent } from './toggle-button/toggle-button.component';
import { AtendeeComponent } from './atendee/atendee.component';
import { MeetupFilterComponent } from './meetup-filter/meetup-filter.component';
import { MeetupListItemComponent } from './meetup-list-item/meetup-list-item.component';
import { MeetupsListPageComponent } from './meetups-list-page/meetups-list-page.component';
import { MeetupPageComponent } from './meetup-page/meetup-page.component';
import { RegConfirmationComponent } from './reg-confirmation/reg-confirmation.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CreateMeetupPageComponent } from './create-meetup-page/create-meetup-page.component';
import { UserDeactivationComponent } from './user-deactivation/user-deactivation.component';

import { ChatPageComponent } from './chat-page/chat-page.component';
import { ChatsListPageComponent } from './chats-list-page/chats-list-page.component';
import { ChatsListItemComponent } from './chats-list-item/chats-list-item.component';
import { ChatMessageComponent } from './chat-message/chat-message.component';

import { ReactiveFormsModule } from '@angular/forms';
import { AchievementCreatePageComponent } from './achievement-create-page/achievement-create-page.component';
import { AchievementPanelComponent } from './achievement-panel/achievement-panel.component';
import { AchievementsPageComponent } from './achievements-page/achievements-page.component';
import { MatDialogModule } from "@angular/material/dialog";
import { TopicsPageComponent } from './topics-page/topics-page.component';
import { TopicPageComponent } from './topic-page/topic-page.component';
import { TopicSearchComponent } from './topic-search/topic-search.component';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { MatFormFieldModule } from "@angular/material/form-field";
import { RescheduleDialogComponent } from './reschedule-dialog/reschedule-dialog.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatInputModule} from "@angular/material/input";
import {MatMomentDateModule} from "@angular/material-moment-adapter";


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
    UserDeactivationComponent,
    ChatPageComponent,
    ChatsListPageComponent,
    ChatsListItemComponent,
    ChatMessageComponent,
    BlogPageComponent,
    BlogListItemComponent,
    BlogListPageComponent,
    BlogCreatePageComponent,
    BlogCommentItemComponent,
    RegConfirmationComponent,
    CreateMeetupPageComponent,
    UserDeactivationComponent,
    AchievementCreatePageComponent,
    AchievementPanelComponent,
    AchievementsPageComponent,
    TopicsPageComponent,
    TopicPageComponent,
    TopicSearchComponent,
    AchievementsPageComponent,
    ConfirmDialogComponent,
    RescheduleDialogComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatProgressSpinnerModule,
    InfiniteScrollModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatInputModule,
    MatMomentDateModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true }],
  bootstrap: [AppComponent],
  entryComponents: [ConfirmDialogComponent, RescheduleDialogComponent]
})
export class AppModule { }
