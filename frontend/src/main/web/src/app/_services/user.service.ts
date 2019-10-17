import {Injectable} from "@angular/core";
import {User} from "../_models";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {Observable, throwError} from "rxjs";
import {environment} from "../../environments/environment";


@Injectable({ providedIn: 'root' })
export class UserService {

  constructor(private http: HttpClient) { }

  register(user: User) : Observable<{}> {
    return this.http.post(`${environment.apiUrl}/api/user/register`, user);
  }

  activate(token: string) : Observable<{}> {
    return this.http.get(`${environment.apiUrl}/api/user/confirm?token=` + token);
  }
}
