import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';


import {User} from '../_models';
import {environment} from "../../environments/environment";

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  private authDataSubject: BehaviorSubject<string>;
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    this.authDataSubject = new BehaviorSubject<string>(localStorage.getItem('authData'));
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  public get currentAuthDataValue(): string {
    return this.authDataSubject.value;
  }

  login(username: string, password: string) {
    let authData = window.btoa(username + ':' + password);
    localStorage.setItem('authData', authData);
    this.authDataSubject.next(authData);

    return this.http.get<any>(`${environment.apiUrl}/api/user/`)
      .pipe(map(user => {
        // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        localStorage.removeItem('authData');
        this.authDataSubject.next(authData);
        return user;
      }));
  }

  logout() : Observable<{}> {
    console.log("logout");
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    localStorage.removeItem('authData');
    this.currentUserSubject.next(null);
    this.authDataSubject.next(null);
    return this.http.get(`${environment.apiUrl}/logout`);
  }
}
