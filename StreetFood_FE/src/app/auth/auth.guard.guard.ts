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

        if (expectedRole && userRole !== expectedRole) {
          this.router.navigate(['/access-denied']); // Reindirizza in caso di accesso non autorizzato
          return false;
        }

        return true;
      }),
      catchError(error => {
        console.error('Errore nel recuperare i dettagli dell\'utente:', error);
        return [false]; // Restituisci false in caso di errore
      })
    );
  }
}
