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

import { CreateMeetupPageComponent } from "./create-meetup-page/create-meetup-page.component";
import { ChatPageComponent } from "./chat-page/chat-page.component";
import { ChatsListPageComponent } from "./chats-list-page/chats-list-page.component";
import { UserDeactivationComponent } from "./user-deactivation/user-deactivation.component";
import { AdminGuard } from "./_helpers/admin.guard";

import { BlogListPageComponent } from "./blog-list-page/blog-list-page.component";
import { BlogPageComponent } from "./blog-page/blog-page.component";
import { BlogCreatePageComponent } from "./blog-create-page/blog-create-page.component";

import { RegConfirmationComponent } from "./reg-confirmation/reg-confirmation.component";

import { AchievementCreatePageComponent } from "./achievement-create-page/achievement-create-page.component";
import { AchievementPanelComponent } from "./achievement-panel/achievement-panel.component";
import { AchievementsPageComponent } from "./achievements-page/achievements-page.component";
import { TopicsPageComponent } from "./topics-page/topics-page.component";
import { TopicPageComponent } from "./topic-page/topic-page.component";

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegisterComponent },
  { path: 'speakers/:id', component: SpeakerPageComponent, canActivate: [AuthGuard] },
  { path: 'speakers', component: SpeakerListPageComponent, canActivate: [AuthGuard] },
  { path: 'create/meetup', component: CreateMeetupPageComponent, canActivate: [AuthGuard] },
  { path: 'meetups/:id', component: MeetupPageComponent, canActivate: [AuthGuard] },
  { path: 'meetups', component: MeetupsListPageComponent, canActivate: [AuthGuard] },
  { path: 'chats/:id', component: ChatPageComponent, canActivate: [AuthGuard] },
  { path: 'chats', component: ChatsListPageComponent, canActivate: [AuthGuard] },
  { path: 'blog', component: BlogListPageComponent, canActivate: [AuthGuard] },
  { path: 'blog/create', component: BlogCreatePageComponent, canActivate: [AuthGuard] },
  { path: 'blog/:id', component: BlogPageComponent, canActivate: [AuthGuard] },
  { path: 'blog/user/:id', component: BlogListPageComponent, canActivate: [AuthGuard] },
  { path: 'confirm', component: RegConfirmationComponent},
  { path: 'deactivation', component: UserDeactivationComponent, canActivate: [AdminGuard] },
  { path: 'achievement/create', component: AchievementCreatePageComponent/*, canActivate: [AdminGuard] */},
  { path: 'achievements', component: AchievementsPageComponent/*, canActivate: [AdminGuard] */},
  { path: 'topics', component: TopicsPageComponent/*, canActivate: [AdminGuard] */},
  { path: 'topics/:id', component: TopicPageComponent/*, canActivate: [AdminGuard] */},

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
