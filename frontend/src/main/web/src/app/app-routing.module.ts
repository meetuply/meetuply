import {Injectable, NgModule} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterModule, RouterStateSnapshot, Routes} from '@angular/router';
import {LandingPageComponent} from "./landing-page/landing-page.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {SpeakerPageComponent} from "./speaker-page/speaker-page.component";
import {SpeakerListPageComponent} from "./speaker-list-page/speaker-list-page.component";
import {AuthenticationService} from "./_services";


const routes: Routes = [
  { path: '', component: LandingPageComponent},
  { path: 'login', component: LoginComponent},
  { path: 'registration', component: RegisterComponent},
  { path: 'speaker', component: SpeakerPageComponent},
  { path: 'speakers', component: SpeakerListPageComponent}
];

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser) {
      // logged in so return true
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['/login']);
    return false;
  }
}

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
