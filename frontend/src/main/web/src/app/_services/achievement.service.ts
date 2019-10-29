import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from "../../environments/environment";
import {Achievement} from "../_models/achievement";


@Injectable({ providedIn: 'root' })
export class AchievementService {

  private achievementApiUrl = `${environment.apiUrl}/api/achievements/`;

  constructor(private http: HttpClient){}

  create(achievement: Achievement): Observable<{}> {
    return this.http.post(this.achievementApiUrl + 'create', achievement);
  }

  getUserAchievements(id:number): Observable<Achievement[]>{
    return this.http.get<Achievement[]>(this.achievementApiUrl + "user/"+ id);
  }
}
