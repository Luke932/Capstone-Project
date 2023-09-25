import { Injectable } from '@angular/core';
import { Like } from '../models/like';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LikeService {
  private likes: Like[] = [];
  baseUrl = environment.baseURL;

  constructor(private http: HttpClient) { }

  setLikes(likes: Like[]): void {
    console.log('chiamata a setLikes');

    this.likes = likes;
    console.log('contenuto di: ', this.likes);
  }

  getLikes(): Like[] {
    console.log('Chiamato getLikes');
    return this.likes;
    console.log('contenuto di: ', this.likes);
  }

  addLike(newLike: Like): void {
    console.log('Chiamato addLike con:', newLike);
    this.likes.push(newLike);
    console.log('contenuto di: ', this.likes);

  }

  removeLike(prodottoId: string): void {
    console.log('Chiamato removeLike con:', prodottoId);
    this.likes = this.likes.filter(like => like.prodottoId !== prodottoId);
  }

  getProdottiConLikeDaUtente(utenteId: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}like/prodotti-con-like/${utenteId}`);
  }

}
