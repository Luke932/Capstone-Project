import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service.service';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {

  constructor(private authSrv: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    return this.authSrv.getUserDetails().pipe(
      map(userDetails => {
        const userRole = userDetails?.ruolo?.nome;
        const expectedRole = route.data['expectedRole'];

        if (!userRole) {
          this.router.navigate(['/access-denied']); // Utente non autenticato
          return false;
        }

        if (expectedRole && userRole !== expectedRole) {
          this.router.navigate(['/access-denied']); // Ruolo non autorizzato
          return false;
        }

        return true;
      }),
      catchError(error => {
        console.error('Errore nel recuperare i dettagli dell\'utente:', error);
        this.router.navigate(['/access-denied']); // Errore nel recupero dei dettagli
        return [false];
      })
    );
  }
}



