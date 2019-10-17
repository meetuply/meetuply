import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AuthenticationService } from '../_services';

@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with basic auth credentials if available
        const authData = this.authenticationService.currentAuthDataValue;

        if (authData) {
            request = request.clone({
                setHeaders: {
                    Authorization: `Basic ${authData}`
                }
            });
        }
        return next.handle(request);
    }
}
