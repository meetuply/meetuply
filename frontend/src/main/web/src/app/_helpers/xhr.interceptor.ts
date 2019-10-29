import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpInterceptor } from '@angular/common/http';


@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest'),
      withCredentials: true // TODO
    });
    return next.handle(xhr);
  }
}
