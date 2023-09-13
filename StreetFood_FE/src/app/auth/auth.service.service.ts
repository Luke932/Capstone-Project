import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';

import { JwtHelperService } from '@auth0/angular-jwt';
import { map, tap } from 'rxjs/operators';
import { AuthData } from './auth-data.interface';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { Utente } from '../models/utente.interface';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isLoggedIn: boolean = false;
  jwtHelper = new JwtHelperService();
  baseUrl = environment.baseURL;
  userProfile!: Utente;
  private authSubj = new BehaviorSubject<null | AuthData>(null);
  utente!: AuthData;
  user$ = this.authSubj.asObservable();
  timeLogout: any;
  user!: AuthData | null;
  private userName = new BehaviorSubject<string>('');
  constructor(private http: HttpClient, private router: Router) {}


  login(data: Utente) {
    return this.http.post<AuthData>(`${this.baseUrl}auth/login`, data).pipe(
      tap((data) => {
        this.isLoggedIn = true;
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

  isLoggedIns(): boolean {
    const token = localStorage.getItem('Token');
    return !this.jwtHelper.isTokenExpired(token);
  }

  getUserDetails(): Observable<any> {
    const token = localStorage.getItem('Token');

    if (token) {
      const userId = this.jwtHelper.decodeToken(token)?.sub; // Estrai l'ID dell'utente dal token

      if (userId) {
        return this.http.get<any>(`${this.baseUrl}utenti/${userId}`);
      }
    }

    return throwError("Impossibile ottenere i dettagli dell'utente.");
  }


  restore() {
    const utenteLS = localStorage.getItem('utente');

    if (utenteLS) {
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
  }) {
    return this.http.post(`${this.baseUrl}auth/register`, data);
  }

  logout() {
    if (this.isLoggedIn) {
      localStorage.removeItem('utente');
      localStorage.removeItem('Token');
      this.router.navigate(['/']);
      this.authSubj.next(null);
      if (this.timeLogout) {
        clearTimeout(this.timeLogout);
      }
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

  setUserProfile(userName: string) {
    this.userName.next(userName);
    localStorage.setItem('username', userName);
  }


  getUserProfile() {
    return this.userName.asObservable();
  }


  getUserData(): Utente {
    return this.userProfile;
  }




}
