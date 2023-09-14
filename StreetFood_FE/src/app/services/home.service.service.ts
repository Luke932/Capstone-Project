import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Luoghi } from '../models/luoghi';

@Injectable({
  providedIn: 'root'
})
export class HomeServiceService {

  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) {}

  getAllLuoghi(page: number) {
    return this.http.get<Luoghi[]>(`${this.baseUrl}luoghi?page=${page}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  getLuogoByTitolo(titolo: string) {
    return this.http.get<Luoghi[]>(`${this.baseUrl}luoghi/titolo/${titolo}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  getLuogoByDescrizione(descrizione: string) {
    return this.http.get<Luoghi[]>(`${this.baseUrl}luoghi/byDescrizione/${descrizione}`)
    .pipe(
      catchError(this.handleError)
    );
  }

  getLuogoByNomeProdotto(prodotto: string) {
    return this.http.get<Luoghi[]>(`${this.baseUrl}luoghi/conProdotto/${prodotto}`)
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
