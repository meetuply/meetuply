import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";
import * as Stomp from 'stompjs'
import * as SockJS from 'sockjs-client'

@Injectable({
	providedIn: 'root'
})
export class NotificationService {

	private notififcationApiUrl = `${environment.apiUrl}/api/ratings/`;

	constructor(private http: HttpClient) {
	}

	stompClient: Stomp.stompClient;

	connect(userId: number, success: (data: any) => void) {


		var socket = new SockJS(`${environment.apiUrl}/meetuply`);


		this.stompClient = Stomp.over(socket);


		this.stompClient.connect({}, frame => {

			console.log('Connected: ' + frame);

			this.subscribe(userId, success);


		});
	}


	subscribe(userId: number, success: (data: any) => void) {
		this.stompClient.subscribe('/notifications/new/' + userId, greeting => {

			success(greeting);

		});
	}

	/*
	getUserRatingAvg(id: number) {
	  return this.http.get<number>(this.notififcationApiUrl + `${id}` + "/avg");
	}
  
	getRatingByTo(idby: number, idto: number): Observable<{}>{
	  return this.http.get(this.notififcationApiUrl + `${idby}` +"/"+`${idto}`);
	}
  
	createRating(rating: Rating): Observable<{}> {
	  return this.http.post<Rating>(this.notififcationApiUrl + `${rating.ratedUser}`, rating);
	}
  
	updateRating(rating: Rating): Observable<{}> {
	  return this.http.put<Rating>(this.notififcationApiUrl + `${rating.ratedUser}`, rating);
	}*/


}
