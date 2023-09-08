import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

import { JwtHelperService } from '@auth0/angular-jwt';
import { map, tap } from 'rxjs/operators';
import { AuthData } from './auth-data.interface';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { Utente } from 'app/models/utente.interface';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
    isLoggedIn = false;
  jwtHelper = new JwtHelperService();
  baseUrl = environment.baseURL;
  userProfile!: Utente;
  private authSubj = new BehaviorSubject<null | AuthData>(null);
  utente!: AuthData;
  user$ = this.authSubj.asObservable();
  timeLogout: any;
  user!: AuthData | null;
  constructor(private http: HttpClient, private router: Router) {}


  login(data: Utente) {
    return this.http.post<AuthData>(`${this.baseUrl}auth/login`, data).pipe(
      tap((data) => {
        this.isLoggedIn = true;
        this.router.navigate(['/clienti']);
        this.authSubj.next(data);
        this.utente = data;
        console.log(this.utente);
        localStorage.setItem('Token', JSON.stringify(data.token));
        localStorage.setItem('utente', JSON.stringify(data));
        this.autologout(data);
        this.userProfile = data.utente;
      })
    );
  }


  restore() {
    const utenteLS = localStorage.getItem('utente');

    if (!utenteLS) {
      return;
    } else {
      const userData: AuthData = JSON.parse(utenteLS);
      if (!this.jwtHelper.isTokenExpired(userData.token)) {
        this.isLoggedIn = true;
        this.authSubj.next(userData);
        this.autologout(userData);
        this.userProfile = userData.utente;
      }

    }
  }


  signup(data: {
    nome: string;
    cognome: string;
    email: string;
    password: string;
    username:string;
    ruoloNome: String
  }) {
    return this.http.post(`${this.baseUrl}auth/registrazione`, data);
  }
  /*signup(data: {
    nome: string;
    cognome: string;
    email: string;
    password: string;
    username: string;
    ruoloNome: string;
  }): Observable<any> {
    return this.http.post('${this.baseUrl}auth/registrazione', data).pipe(
      map((response) => {
        // Se la registrazione è andata a buon fine, setta isloggedin a true
        this.isLoggedIn = true;
        return response; // Passa la risposta originale all'uso successivo se necessario
      })
    );
  }*/

  logout() {
    this.authSubj.next(null);
    localStorage.removeItem('utente');
    localStorage.removeItem('Token');
    this.router.navigate(['/']);
    if (this.timeLogout) {
      clearTimeout(this.timeLogout);
    }
  }
  autologout(data: AuthData) {
    const expirationDate = this.jwtHelper.getTokenExpirationDate(
      data.token
    ) as Date;
    const expirationMilliseconds =
      expirationDate.getTime() - new Date().getTime();
    this.timeLogout = setTimeout(() => {
      this.logout();
    }, expirationMilliseconds);
  }

  getUserData(): Utente {
    return this.userProfile;
  }
}