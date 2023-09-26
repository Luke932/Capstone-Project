import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { UtentiInterface } from '../models/utenti.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UtenteService {

  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) {}

  getAllUtenti(): Observable<UtentiInterface[]> {
    return this.http.get<UtentiInterface[]>(`${this.baseUrl}utenti`);
  }

  updateUser(userId: string, payload: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}auth/${userId}`, payload);
  }

  saveUserWithFile(formData: FormData){
    return this.http.post<UtentiInterface>(`${this.baseUrl}auth/register/admin`, formData);
  }

  registerUserWithFile(formData: FormData) {
    return this.http.post<UtentiInterface>(`${this.baseUrl}auth/register/user`, formData);
  }

  deleteUser(userId: string) {
    return this.http.delete(`${this.baseUrl}utenti/${userId}`);
  }


}
