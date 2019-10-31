import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from "../../environments/environment";
import {Achievement} from "../_models/achievement";


@Injectable({providedIn: 'root'})
export class AchievementService {

  private achievementApiUrl = `${environment.apiUrl}/api/achievements/`;

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Achievement[]> {
    return this.http.get<Achievement[]>(this.achievementApiUrl);
  }

  // getAchievementsChunk(startRow: number, endRow: number): Observable<Achievement[]> {
  //   return this.http.get<Achievement[]>(this.achievementApiUrl + `${startRow}` + "/" + `${endRow}`)
  // }

  create(achievement: Achievement) {
    return this.http.post<number>(this.achievementApiUrl + 'create', achievement);
  }

  createForMeetupsSameQuantity(formData: FormData) {
    return this.http.post<any>(this.achievementApiUrl + 'meetups-topic/same', formData);
  }

  getUserAchievements(id: number): Observable<Achievement[]> {
    return this.http.get<Achievement[]>(this.achievementApiUrl + "user/" + id);
  }

  deleteFromAchievement(id: number) {
    return this.http.delete(this.achievementApiUrl + id);
  }
}
