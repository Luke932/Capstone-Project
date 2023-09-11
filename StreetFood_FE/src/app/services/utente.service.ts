import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Utente } from '../models/utente.interface';

@Injectable({
  providedIn: 'root'
})
export class UtenteService {

  baseUrl = environment.baseURL;
  constructor(private http: HttpClient) {}

  getAllUtenti() {
    return this.http.get<Utente[]>(`${this.baseUrl}utenti`);
  }
}
