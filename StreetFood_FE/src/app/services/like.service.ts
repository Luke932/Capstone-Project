import { Injectable } from '@angular/core';
import { Like } from '../models/like';

@Injectable({
  providedIn: 'root'
})
export class LikeService {
  private likes: Like[] = [];

  constructor() { }

  setLikes(likes: Like[]): void {
    this.likes = likes;
  }

  getLikes(): Like[] {
    return this.likes;
  }

  addLike(like: Like): void {
    this.likes.push(like);
  }

  removeLike(prodottoId: string): void {
    this.likes = this.likes.filter(like => like.prodottoId !== prodottoId);
  }
}
