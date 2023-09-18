import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Like } from 'src/app/models/like';
import { LikeResponse } from 'src/app/models/like-response';
import { Prodotti } from 'src/app/models/prodotti';
import { LikeService } from 'src/app/services/like.service';
import { ProdottiService } from 'src/app/services/prodotti.service';

@Component({
  selector: 'app-prodotti',
  templateUrl: './prodotti.component.html',
  styleUrls: ['./prodotti.component.scss']
})
export class ProdottiComponent implements OnInit {
  prodotti: Prodotti[] = [];
  currentPage: number = 0;
  totalPages: number = 0;
  totalElements: number = 0;
  likes: Like[] = [];
  isLiked: boolean[] = [];

  constructor(private prodottiSrv: ProdottiService, private likeService: LikeService) { }

  ngOnInit(): void {
    this.getProdotti(0);
    this.loadLikesFromSessionStorage(); // Carica i like memorizzati
    this.updateIsLiked();
  }

  private updateIsLiked() {
    console.log('Updating isLiked'); // Aggiunto per debug

    this.isLiked = this.prodotti.map(prodotto => {
      return this.likes.some(like => like.prodottoId === prodotto.id);
    });

    console.log(this.isLiked); // Aggiunto per debug
  }


  getProdotti(page: number): void {
    this.prodottiSrv.getAllProdotti(page).subscribe((data: any) => {
      if (Array.isArray(data.content)) {
        this.prodotti = data.content.map((prodotto: Prodotti) => {
          prodotto.isLiked = false;
          return prodotto;
        });

        this.isLiked = this.prodotti.map(_ => false);

        this.totalElements = data.totalElements;
        this.totalPages = data.totalPages;
        this.currentPage = page;
      } else {
        console.error("I dati ricevuti non sono un array", data);
      }
    },
    (error: HttpErrorResponse) => {
      console.error('Errore nella richiesta HTTP:', error);
    });
  }

  toggleLike(prodotto: Prodotti, index: number) {
    const utenteId = this.prodottiSrv.getId() || '';
    console.log(utenteId);

    if (prodotto.id !== undefined) {
      const likeId = prodotto.likeId;
      console.log(likeId);

      if (likeId !== undefined) {
        this.prodottiSrv.deleteLike(likeId).subscribe(
          (response) => {
            console.log(response);
            prodotto.likeId = undefined;
            this.isLiked[index] = false;
            this.updateLikesArray(prodotto); // Aggiorna l'array dei like
            this.saveLikesToSessionStorage(); // Salva i like nel localStorage
          },
          (error) => {
            console.error('Errore durante la cancellazione del like:', error);
          }
        );
      } else {
        this.prodottiSrv.createLike(utenteId, prodotto.id).subscribe(
          (response) => {
            if (response) {
              prodotto.likeId = response;
              this.isLiked[index] = true;
              this.updateLikesArray(prodotto); // Aggiorna l'array dei like
              this.saveLikesToSessionStorage(); // Salva i like nel localStorage
            } else {
              console.error('Errore: likeId non ricevuto dal backend');
            }
          },
          (error) => {
            console.error('Errore durante la creazione del like:', error);
          }
        );
      }
    } else {
      console.error('ID del prodotto non definito');
    }
  }

  private updateLikesArray(prodotto: Prodotti) {
    const productId = prodotto.id;

    if (productId !== undefined) {
      const likes = this.likeService.getLikes(); // Usa il servizio LikeService
      const index = likes.findIndex(like => like.prodottoId === productId);

      if (index !== -1) {
        this.likeService.removeLike(productId);
      } else {
        const utenteId = this.prodottiSrv.getId() || '';
        const newLike: Like = {
          prodottoId: productId,
          utente: utenteId,
          dataLike: new Date()
        };
        this.likeService.addLike(newLike);
      }
    } else {
      console.error('ID del prodotto non definito');
    }
  }




  private saveLikesToSessionStorage() {
    sessionStorage.setItem('likes', JSON.stringify(this.likes));
  }
  private loadLikesFromSessionStorage() {
    console.log('Loading likes from sessionStorage'); // Aggiunto per debug

    const likesString = sessionStorage.getItem('likes');
    console.log(likesString); // Aggiunto per debug

    if (likesString) {
      const likes = JSON.parse(likesString);
      this.likes = likes; // Assegna i likes
      this.likeService.setLikes(likes);
      this.updateIsLiked();
    }
  }


}
