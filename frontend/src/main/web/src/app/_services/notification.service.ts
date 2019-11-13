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

	private notififcationApiUrl = `${environment.apiUrl}/api/notifications/`;

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

}
