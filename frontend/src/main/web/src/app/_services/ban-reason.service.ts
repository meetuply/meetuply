import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {Observable, throwError, of} from "rxjs";
import {environment} from "../../environments/environment";
import {BanReason} from "../_models/banReason";


@Injectable({providedIn: 'root'})
export class BanReasonService {

  private banReasonApiUrl = `${environment.apiUrl}/api/ban_reasons/`;

  constructor(private http: HttpClient) {
  }

  /** GET ban reasons from the server */
  getAll():Observable<BanReason[]> {
    return this.http.get<BanReason[]>(this.banReasonApiUrl);
  }

  /** GET ban reasons by id. Will 404 if id not found */
  getBanReason(id: number): Observable<BanReason> {
    return this.http.get<BanReason>(this.banReasonApiUrl + `${id}`);
  }

  /** PUT: update the ban reason on the server */
  updateBanReason (banReason: BanReason): Observable<BanReason> {
    return this.http.put<BanReason>(this.banReasonApiUrl + `${banReason.banReasonId}`, banReason);
  }

  /** POST: add a new ban reason to the server */
  createBanReason(banReason: BanReason): Observable<BanReason> {
    return this.http.post<BanReason>(this.banReasonApiUrl, banReason);
  }

  /** DELETE: delete the ban reason from the server */
  deleteBanReason(banReason: BanReason): Observable<BanReason> {
    return this.http.delete<BanReason>(this.banReasonApiUrl + `${banReason.banReasonId}`);
  }

  /* GET ban reasons whose name contains search term */
  searchBanReasons(term: string): Observable<BanReason[]> {
    if (!term.trim() || term.includes(";")) {
      // if not search term, return empty ban reason array.
      return of([]);
    }
    return this.http.get<BanReason[]>(this.banReasonApiUrl + `banReasonName/${term}`);
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
