import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Prodotti } from '../models/prodotti';
import { catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProdottiService {
  baseUrl = environment.baseURL;


  constructor(private http: HttpClient) { }

  getAllProdotti(page: number) {
    return this.http.get<Prodotti[]>(`${this.baseUrl}prodotti?page=${page}`)
    .pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('Errore del client o di rete:', error.error.message);
    } else if (error.status === 403) {
      console.error('Accesso vietato. Assicurati di essere autenticato correttamente.');
    } else {
      console.error(
        `Codice di ritorno dal server ${error.status}, ` +
        `corpo dell'errore: ${error.error}`);
    }
    return throwError(
      'Qualcosa è andato storto; riprova più tardi.');
  }
}
