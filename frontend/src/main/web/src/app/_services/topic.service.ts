import { Injectable } from "@angular/core";
import { Topic } from "../_models/topic";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { Observable, throwError } from "rxjs";


@Injectable({ providedIn: 'root' })
export class TopicService {

	constructor(private http: HttpClient) { }

	getTopics(): Observable<Topic[]> {
		return this.http.get<Topic[]>('/api/topics');
	}

}
