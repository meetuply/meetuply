import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {error} from "util";

@Injectable({ providedIn: 'root' })
export class StateService {

  private statesApiUrl = `${environment.apiUrl}/api/states/`;
  states = null;

  constructor(private http: HttpClient) {
    this.loadStates();
  }

  loadStates() {
    this.http.get<number>(this.statesApiUrl).subscribe(
      data => this.states = data,
      error => console.log(error)
    );
  }
}
