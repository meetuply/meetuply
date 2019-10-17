import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AuthenticationService } from '../_services';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {

            if (err.status === 401 && this.authenticationService.currentAuthDataValue == null) {
                // auto logout if 401 response returned from api
                this.authenticationService.logout();
                location.reload(true);
            }

            if (err.status === 401 && this.authenticationService.currentAuthDataValue != null) {
                const error = "Invalid email or password";
                this.authenticationService.logout();
                return throwError(error);
            }

            console.log(err);
            const error = (err.error.error != null && err.error.errors[0].defaultMessage)
                          || err.error.message || err.statusText;
            return throwError(error);
        }))
    }
}
