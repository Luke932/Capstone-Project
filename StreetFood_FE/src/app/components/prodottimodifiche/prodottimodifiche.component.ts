import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { SafeUrl, DomSanitizer } from '@angular/platform-browser';
import { Commento } from 'src/app/models/commento';
import { Like } from 'src/app/models/like';
import { Prodotti } from 'src/app/models/prodotti';
import { CommentoService } from 'src/app/services/commento.service';
import { LikeService } from 'src/app/services/like.service';
import { ProdottiService } from 'src/app/services/prodotti.service';

@Component({
  selector: 'app-prodottimodifiche',
  templateUrl: './prodottimodifiche.component.html',
  styleUrls: ['./prodottimodifiche.component.scss']
})
export class ProdottimodificheComponent implements OnInit {
  prodotti: Prodotti[] = [];
  currentPage: number = 0;
  totalPages: number = 0;
  totalElements: number = 0;
  likes: Like[] = [];
  isLiked: boolean[] = [];
  nuovoTestoCommento: string = ''; // Aggiungi questa proprietà per il nuovo testo del commento
  commentoId: string = '';
  utenteId: string = '';
  testoCommento: string = '';
  commentoInModifica: Commento | null = null; // Commento attualmente in modifica
  commentoDaEliminare: Commento | null = null;
  showCommentForm: boolean = false;
  prodottoInCommento: Prodotti | null = null;
   userPhotoUrl!: SafeUrl | null;
   mostraForm: boolean = false;
   formProdotto: any = {};
   mostraRisultati: boolean = false;

  constructor(private prodottiSrv: ProdottiService, private likeService: LikeService,private commentoService: CommentoService,private domSan: DomSanitizer) {
    this.likes = this.likeService.getLikes();
        console.log('Likes nel costruttore:', this.likes); // Aggiunto per debug
  }

  ngOnInit(): void {
    this.utenteId = this.prodottiSrv.getId() || '';
    this.getProdotti(0);
    this.updateIsLiked();

    // Aggiungi questo
    this.prodotti.forEach(prodotto => {
      if (prodotto.id) {
        this.prodottiSrv.getLikesByUserAndProduct(this.utenteId, prodotto.id).subscribe(
          (likes) => {
            const likedByUser = likes.length > 0;
            if (likedByUser) {
              prodotto.isLiked = true;
            }
          },
          (error) => {
            console.error(`Errore durante il recupero dei like per il prodotto ${prodotto.id}:`, error);
          }
        );
      }
    });

    const imageByte = localStorage.getItem('userPhotoUrl');
    console.log(imageByte);

    if (imageByte) {
      const byteCharacters = atob(imageByte);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/jpeg' });

      this.userPhotoUrl = this.domSan.bypassSecurityTrustUrl(URL.createObjectURL(blob));
    }

    console.log(this.userPhotoUrl);




    this.prodotti.forEach(prodotto => {
      prodotto.mostraFormCommento = false; // Inizializza a false
      // ...
    });

  }







  private updateIsLiked() {
    console.log('Updating isLiked');

    const likes = this.likeService.getLikes(); // Ottieni l'array dei like dal servizio

    this.isLiked = this.prodotti.map(prodotto => {
      const likedInSession = likes.some(like => like.prodottoId === prodotto.id);
      return likedInSession;
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

        this.updateIsLiked(); // Aggiorna lo stato dei like
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
    const likeId = prodotto.likeId;

    if (likeId !== undefined) {
      this.prodottiSrv.deleteLike(likeId).subscribe(
        (response) => {
          console.log(response);
          prodotto.likeId = undefined;
          this.isLiked[index] = false;
          this.updateLikesArray(prodotto);
          localStorage.removeItem('likes');
          this.isLiked = [...this.isLiked];
        },
        (error) => {
          console.error('Errore durante la cancellazione del like:', error);
        }
      );
    } else if (prodotto.id) {
      const alreadyLiked = this.likes.some(like => like.prodottoId === prodotto.id);
      if (!alreadyLiked) {
        this.prodottiSrv.createLike(utenteId, prodotto.id).subscribe(
          (response) => {
            if (response) {
              prodotto.likeId = response;
              this.isLiked[index] = true;
              this.updateLikesArray(prodotto);
              localStorage.setItem('likes', JSON.stringify(this.likes));
              this.isLiked = [...this.isLiked];
            } else {
              console.error('Errore: likeId non ricevuto dal backend');
            }
          },
          (error) => {
            console.error('Errore durante la creazione del like:', error);
          }
        );
      } else {
        // L'utente ha già messo "mi piace", quindi rimuoviamo il like
        this.prodottiSrv.getLikesByUserAndProduct(utenteId, prodotto.id).subscribe(
          (likes) => {
            if (likes && likes.length > 0) {
              const likeToRemove = likes[0];
              if (likeToRemove.id) {
                this.prodottiSrv.deleteLike(likeToRemove.id).subscribe(
                  (response) => {
                    console.log(response);
                    prodotto.likeId = undefined;
                    this.isLiked[index] = false;
                    this.updateLikesArray(prodotto);
                    localStorage.removeItem('likes');
                    this.isLiked = [...this.isLiked];
                  },
                  (error) => {
                    console.error('Errore durante la cancellazione del like:', error);
                  }
                );
              }
            }
          },
          (error) => {
            console.error('Errore durante il recupero dei like:', error);
          }
        );
      }
    }
  }








  private updateLikesArray(prodotto: Prodotti) {
    const productId = prodotto.id;

    if (productId !== undefined) {
      const likes = this.likeService.getLikes(); // Usa il servizio LikeService
      const index = likes.findIndex(like => like.prodottoId === productId);

      if (index !== -1) {
        this.likeService.removeLike(productId);
        console.log(this.likes);

      } else {
        const utenteId = this.prodottiSrv.getId() || '';
        const newLike: Like = {
          prodottoId: productId,
          utente: utenteId,
          dataLike: new Date()
        };
        this.likeService.addLike(newLike);
        console.log(this.likes);

      }

      this.likes = this.likeService.getLikes(); // Aggiorna this.likes
     // Salva i like nel localStorage
    } else {
      console.error('ID del prodotto non definito');
    }
  }













  createCommento(utenteId: string, prodotto: Prodotti) {
    if (prodotto.id) {
      this.prodottoInCommento = prodotto;
      this.commentoService.createCommento(utenteId, prodotto.id, this.nuovoTestoCommento).subscribe(
        (commento) => {
          console.log('Commento creato con successo:', commento);
          if (!prodotto.commenti) {
            prodotto.commenti = [];
          }
          prodotto.commenti.push(commento);
          this.nuovoTestoCommento = '';


          prodotto.mostraFormCommento = false;
        },
        (error) => {
          console.error('Errore durante la creazione del commento:', error);
        }
      );
    } else {
      console.error('ID del prodotto non definito');
    }
  }


  mostraFormCommento(prodotto: Prodotti) {
    prodotto.mostraFormCommento = !prodotto.mostraFormCommento; // Inverti lo stato del form
    this.prodottoInCommento = prodotto; // Imposta il prodotto corrente
  }

  editCommento(commento: Commento, prodotto: Prodotti) {
    this.prodottoInCommento = prodotto;
    this.commentoInModifica = commento;
    this.formDati.nuovoTestoCommento = commento.testoCommento;
}



  formDati = {
    nuovoTestoCommento: ''
  };

  // ...

  updateCommento() {
    console.log('Commento in modifica:', this.commentoInModifica);
    if (this.commentoInModifica) {
      console.log('Nuovo testo commento:', this.formDati.nuovoTestoCommento);
      this.commentoService.updateCommentoById(this.commentoInModifica.id, this.formDati.nuovoTestoCommento).subscribe(
        (commento) => {
          console.log('Commento aggiornato con successo:', commento);
          this.commentoInModifica = null;
          this.formDati.nuovoTestoCommento = '';
          console.log('Metodo updateCommento chiamato.');
          console.log('Commento in modifica:', this.commentoInModifica);
          console.log('Nuovo testo commento:', this.formDati.nuovoTestoCommento); // Resetta il campo dopo l'aggiornamento del commento
        },
        (error) => {
          console.error('Errore durante l\'aggiornamento del commento:', error);
        }
      );
    }
  }


  deleteCommento(prodotto: Prodotti, commentoId: string) {
    this.commentoService.deleteCommento(commentoId).subscribe(
      () => {
        console.log('Commento cancellato con successo');
        if (prodotto.commenti) {
          prodotto.commenti = prodotto.commenti.filter(commento => commento.id !== commentoId);
        }
      },
      (error) => {
        console.error('Errore durante la cancellazione del commento:', error);
      }
    );
  }



  creaProdotto(prodotto: any) {
    this.prodottiSrv.createProdotto(prodotto).subscribe(
      (response) => {
        this.prodotti.push(response);
        console.log('Prodotto creato con successo', response);
      },
      (error) => {
        console.error('Errore durante la creazione del prodotto', error);
      }
    );
  }


  aggiornaProdotto(id: string, nuovoProdotto: any) {
  this.prodottiSrv.updateProdotto(id, nuovoProdotto).subscribe(
    (response) => {
      const indice = this.prodotti.findIndex(lg => lg.id === id);
      if (indice !== -1) {
        this.prodotti[indice] = response;
      }
      console.log('Prodotto aggiornato con successo', response);
    },
    (error) => {
      if (error.status === 404) {
        console.error('Prodotto non trovato con ID:', id);
      } else {
        console.error('Errore durante l\'aggiornamento del prodotto', error);
      }
    }
  );
}




  eliminaProdotto(id: string) {
    this.prodottiSrv.deleteProdotti(id).subscribe(
      () => {
        this.prodotti = this.prodotti.filter(lg => lg.id !== id);
        console.log('Prodotto eliminato con successo');
      },
      (error) => {
        console.error('Errore durante l\'eliminazione del prodotto', error);
      }
    );
  }


  mostraFormCreazione(): void {
    this.mostraForm = true;
    this.formProdotto = {};
  }

  mostraFormAggiornamento(prodotto: any): void {
    this.mostraForm = true;
    this.formProdotto = { ...prodotto };
  }

  inviaForm(): void {
    if (this.formProdotto.id) {

      this.aggiornaProdotto(this.formProdotto.id, this.formProdotto);
    } else {

      this.creaProdotto(this.formProdotto);
    }
    this.mostraForm = false;
  }
}




