import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, catchError, of, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Commento } from '../models/commento';

@Injectable({
  providedIn: 'root'
})
export class CommentoService {
  private apiUrl = environment.baseURL;

  constructor(private http: HttpClient) { }



  createCommento(utenteId: string, prodottoId: string, testoCommento: string): Observable<any> {
    const params = new HttpParams()
      .set('utenteId', utenteId)
      .set('prodottoId', prodottoId)
      .set('testoCommento', testoCommento)

    return this.http.post<Commento[]>(`${this.apiUrl}commenti`, {}, { params })
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

  updateCommentoById(commentoId: string, nuovoTestoCommento: string): Observable<any> {
    const params = new HttpParams()
    .set('nuovoTestoCommento', nuovoTestoCommento)

    return this.http.put<Commento[]>(`${this.apiUrl}commenti/${commentoId}`, {}, { params })
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



  deleteCommento(commentoId: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}commenti/${commentoId}`);
  }
}
