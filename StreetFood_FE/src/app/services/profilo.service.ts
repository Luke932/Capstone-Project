import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProfiloService {
  baseUrl = environment.baseURL;
  componenteFiglioSelezionato: string = '';

  constructor(private http: HttpClient) { }


}
