import { Injectable } from "@angular/core";
import { Message } from "../_models";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { Observable, throwError } from "rxjs";
import { environment } from "../../environments/environment";


@Injectable({ providedIn: 'root' })
export class ChatService {


	//private languageApiUrl = `${environment.apiUrl}/api/languages/`;
	private chatApiUrl = 'http://localhost:8080/api/chat';

	constructor(private http: HttpClient) { }


	//TODO: redo later
	/*

	getMeetupsChunk(startRow: number, endRow: number): Observable<Meetup[]> {
    return this.http.get<Meetup[]>(this.meetupApiUrl + `${startRow}` + "/" + `${endRow}`)
  }*/


	getRoomMessages(roomId: number): Observable<Message[]> {
		return this.http.get<Message[]>(this.chatApiUrl + "/rooms/" + roomId + "/messages");
	}

	getRoomMembers(roomId: number): Observable<number[]> {
		return this.http.get<number[]>(this.chatApiUrl + "/rooms/" + roomId + "/members");
	}

	haveCommonRoom(userId1: number, userId2: number): Observable<number> {
		return this.http.get<number>(this.chatApiUrl + "/rooms/common/" + userId1 + "/" + userId2);
	}

	createCommmonRoom(id1: number, id2: number): Observable<number> {
		return this.http.post<number>(this.chatApiUrl + "/rooms/common/" + id1 + "/" + id2, "");
	}


	private handleError(error: HttpErrorResponse) {
		if (error.error instanceof ErrorEvent) {
			console.error('An error occurred:', error.error.message);
		} else {
			console.error(
				`Backend returned code ${error.status}, ` +
				`body was: ${error.error}`);
		}
		return throwError(
			'Something bad happened; please try again later.');
	};

}
