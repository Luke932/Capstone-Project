import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Prodotti } from '../models/prodotti';
import { Luoghi } from '../models/luoghi';

@Injectable({
  providedIn: 'root'
})
export class HomeServiceService {

  baseUrl = environment.baseURL;
  constructor(private http: HttpClient) {}

  getAllLuoghi() {
    return this.http.get<Luoghi[]>(`${this.baseUrl}luoghi`);
  }
}
