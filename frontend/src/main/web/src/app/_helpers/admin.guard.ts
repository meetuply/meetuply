import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from "../_services";
import {AuthGuard} from "./auth.guard";


@Injectable({ providedIn: 'root' })
export class AdminGuard extends AuthGuard {

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    if (super.canActivate(route, state)) {
      const currentUser = this.authenticationService.currentUserValue;
      if (currentUser.role.roleName == "admin") {
        return true;
      }
    } else return false;

  }
}

