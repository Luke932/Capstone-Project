import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Prodotti } from '../models/prodotti';
import { Observable, catchError, of, throwError } from 'rxjs';
import { Like } from '../models/like';

@Injectable({
  providedIn: 'root'
})
export class ProdottiService {
  baseUrl = environment.baseURL;


  constructor(private http: HttpClient) { }

  getAllProdotti(page: number) {
    const utenteId = this.getId(); // Recupera l'ID dell'utente
    const params = new HttpParams().set('utenteId', utenteId || ''); // Aggiungi l'ID dell'utente come parametro
    return this.http.get<Prodotti[]>(`${this.baseUrl}prodotti?page=${page}`, { params })
      .pipe(
        catchError(this.handleError)
      );
  }


  getLikeByUserAndProduct(utenteId: string, prodottoId: string) {
    const url = `${this.baseUrl}like?utenteId=${utenteId}&prodottoId=${prodottoId}`;
    return this.http.get<Like[]>(url).pipe(
      catchError(this.handleError)
    );
  }





  createAndDeleteLike(utenteId: string, prodottoId: string) {
    return this.http.post<any>(`${this.baseUrl}like`, { utenteId, prodottoId });
  }

  createLike(utenteId: string, prodottoId: string): Observable<any> {
    const params = new HttpParams()
      .set('utenteId', utenteId)
      .set('prodottoId', prodottoId);

    return this.http.post<any>(`${this.baseUrl}like/create`, {}, { params })
      .pipe(
        catchError(error => {
          if (error instanceof HttpErrorResponse && error.status === 200 && typeof error.error.text === 'string') {
            return of(error.error.text);
          } else {
            return throwError('Errore durante la creazione del like');
          }
        })
      );
  }


  deleteLikes(likeId: string): Observable<string> {
    return this.http.delete<string>(`${this.baseUrl}like/delete`, { params: { likeId } });
  }


  getId() {
    return localStorage.getItem('id');
  }

  deleteLike(likeId: string) {
    return this.http.delete(`${this.baseUrl}like/${likeId}`);
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
