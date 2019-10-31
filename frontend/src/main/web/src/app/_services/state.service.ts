import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {error} from "util";
import {State} from "../_models/state";

@Injectable({ providedIn: 'root' })
export class StateService {

  private statesApiUrl = `${environment.apiUrl}/api/states/`;
  states = {};

  constructor(private http: HttpClient) {
    this.loadStates();
  }

  loadStates() {
    this.http.get<State[]>(this.statesApiUrl).subscribe(
      data => {
        data.forEach(s => this.states[s.stateId] = s.name);
      },
      error => console.log(error)
    );
  }
}
