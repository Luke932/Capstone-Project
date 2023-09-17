import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Like } from 'src/app/models/like';
import { LikeResponse } from 'src/app/models/like-response';
import { Prodotti } from 'src/app/models/prodotti';
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


  constructor(private prodottiSrv: ProdottiService) { }
  isLiked: boolean[] = [];



ngOnInit(): void {
  this.getProdotti(0);
this.loadLikedFromStorage();
}







getProdotti(page: number): void {
  this.prodottiSrv.getAllProdotti(page).subscribe((data: any) => {
    if (Array.isArray(data.content)) {
      this.prodotti = data.content.map((prodotto: Prodotti) => {
        prodotto.isLiked = false; // Inizializza isLiked a false per ogni prodotto
        return prodotto;
      });

      // Inizializza isLiked per ogni prodotto
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

toggleLike(prodotto: Prodotti): void {
  const prodottoId = prodotto.id!;
  const utenteId = this.prodottiSrv.getId();

  if (utenteId) {
    this.prodottiSrv.getLikeByUserAndProduct(utenteId, prodottoId).subscribe(
      (response: any) => {
        console.log("Risultato di getLikeByUserAndProduct:", response);
        const like = response.content;
        const index = this.prodotti.indexOf(prodotto);
        const likeId = like[0]?.id;

        console.log(likeId);

        if (likeId !== undefined) {
          this.prodottiSrv.deleteLike(likeId).subscribe(() => {
            this.updateLikedInStorage(prodottoId, false);

            if (index !== -1) {
              this.isLiked[index] = false;
              prodotto.isLiked = false;
            }
          });
        } else {
          this.prodottiSrv.createLike(utenteId, prodottoId).subscribe(() => {
            this.updateLikedInStorage(prodottoId, true);

            if (index !== -1) {
              this.isLiked[index] = true;
              prodotto.isLiked = true;
            }
          });
        }
      },
      (error) => {
        console.error("Errore durante la chiamata getLikeByUserAndProduct:", error);
      }
    );
  } else {
    console.error("ID dell'utente non valido");
  }
}


loadLikedFromStorage(): void {
  const likedItems = JSON.parse(localStorage.getItem('likedItems') || '[]');
  console.log('Liked items:', likedItems);
  if (likedItems.length > 0) {
    this.prodotti.forEach((prodotto) => {
      prodotto.isLiked = likedItems.includes(prodotto.id);
    });
  }
}







updateLikedInStorage(prodottoId: string, isLiked: boolean): void {
  const likedItems = JSON.parse(localStorage.getItem('likedItems') || '[]');

  if (isLiked) {
    if (!likedItems.includes(prodottoId)) {
      likedItems.push(prodottoId);
    }
  } else {
    const index = likedItems.indexOf(prodottoId);
    if (index > -1) {
      likedItems.splice(index, 1);
    }
  }

  localStorage.setItem('likedItems', JSON.stringify(likedItems));

}



/*


toggleLike(prodotto: Prodotti): void {
  const prodottoId = prodotto.id!;
  const utenteId = this.prodottiSrv.getId();

  if (utenteId) {
    this.prodottiSrv.getLikeByUserAndProduct(utenteId, prodottoId).subscribe((like) => {
    const index = this.prodotti.indexOf(prodotto);

      if (like.length > 0) {
        const likeId = like[0].id;
        this.prodottiSrv.createAndDeleteLike(utenteId, prodottoId).subscribe(() => {
          this.updateLikedInStorage(prodottoId, false);

          if (index !== -1) {
            this.isLiked[index] = false;
            prodotto.isLiked = false;
          }
        });
      } else {
        this.prodottiSrv.createAndDeleteLike(utenteId, prodottoId).subscribe(() => {
          this.updateLikedInStorage(prodottoId, true);

          if (index !== -1) {
            this.isLiked[index] = true;
            prodotto.isLiked = true;
          }
        });
      }
    });
  } else {
    console.error('ID dell\'utente non valido');
  }
}













updateLikedInStorage(prodottoId: string, isLiked: boolean): void {
  const likedItems = JSON.parse(localStorage.getItem('likedItems') || '[]');

  if (isLiked) {
    if (!likedItems.includes(prodottoId)) {
      likedItems.push(prodottoId);
    }
  } else {
    const index = likedItems.indexOf(prodottoId);
    if (index > -1) {
      likedItems.splice(index, 1);
    }
  }

  localStorage.setItem('likedItems', JSON.stringify(likedItems));

}*/
}

























