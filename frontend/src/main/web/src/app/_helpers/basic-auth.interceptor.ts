import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AuthenticationService } from '../_services';

@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {

    public authData: string;

    constructor(private authenticationService: AuthenticationService) {
      this.authenticationService.authData.subscribe(
        data => {
          this.authData = data;
        }
      );
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if (this.authData) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Basic ${this.authData}`
                }
            });
        }
        return next.handle(request);
    }
}
