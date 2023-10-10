import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Utente } from '../models/utente.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfiloService {
  baseUrl = environment.baseURL;
  componenteFiglioSelezionato: string = '';

  constructor(private http: HttpClient) { }

  getUserById(userId: string): Observable<Utente[]> {
    return this.http.get<Utente[]>(`${this.baseUrl}utenti/${userId}`);
  }

}
