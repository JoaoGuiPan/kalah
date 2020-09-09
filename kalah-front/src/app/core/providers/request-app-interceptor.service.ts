import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { LoaderService } from './loader.service';
import {Observable, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RequestAppInterceptorService implements HttpInterceptor {

  constructor(private loaderService: LoaderService, private router: Router) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.loaderService.show();

    return next.handle(request).pipe(
      tap(event => {
        if (event instanceof HttpResponse) {
          this.loaderService.hide();
        }
        return event;
      }),
      catchError((error: HttpErrorResponse) => {
        this.loaderService.hide();
        this.router.navigateByUrl('');
        return throwError(error);
      }));
  }

}
