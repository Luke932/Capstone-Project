import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service.service';
import { switchMap, take } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    token!: string | null;
  constructor(private authSrv: AuthService, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return this.authSrv.user$.pipe(
      take(1),
      switchMap(user => {
        if (!user) {
          return next.handle(request);
        }
        this.token = localStorage.getItem('Token');
        if (this.token) {
          const tokenValore = JSON.parse(this.token);
          console.log('TokenValore:', tokenValore);
          const newRequest = request.clone({
            setHeaders: {
              'Authorization': `Bearer ${tokenValore}`
            }
          });

          return next.handle(newRequest);
        } else {
          this.router.navigate(['']);
          return next.handle(request);
        }
      })
    );
  }

}
