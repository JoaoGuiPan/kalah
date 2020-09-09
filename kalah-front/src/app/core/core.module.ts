import { NgModule } from '@angular/core';
import { MatchService } from './providers/match.service';
import { LoaderService } from './providers/loader.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { RequestAppInterceptorService } from './providers/request-app-interceptor.service';

export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: RequestAppInterceptorService, multi: true },
];

@NgModule({
  declarations: [],
  imports: [],
  providers: [
    LoaderService,
    httpInterceptorProviders,
    MatchService,
  ]
})
export class CoreModule { }
