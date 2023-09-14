import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Luoghi } from 'src/app/models/luoghi';
import { FooterService } from 'src/app/services/footer.service';
import { HomeServiceService } from 'src/app/services/home.service.service';

@Component({
  selector: 'app-home.modifiche',
  templateUrl: './home.modifiche.component.html',
  styleUrls: ['./home.modifiche.component.scss']
})
export class HomeModificheComponent implements OnInit {
  luoghi: Luoghi[] = [];
  luoghiTrovati: Luoghi [] = [];
  currentPage: number = 0;
  totalPages: number = 0;
  totalElements: number = 0;
  titoloDaCercare: string = '';
  mostraRisultati: boolean = false;
  tipoRicerca: string = 'descrizione';
  mostraForm: boolean = false;
formLuogo: any = {};

  constructor(private homeSrv: HomeServiceService, private footSrv: FooterService) {
    this.footSrv.setShowFooter(true);
  }

  ngOnInit(): void {
    this.getLuoghi(0);
  }

  getLuoghi(page: number): void {
    this.homeSrv.getAllLuoghi(page).subscribe((data: any) => {
      if (Array.isArray(data.content)) {
        this.luoghi = data.content;
        this.totalElements = data.totalElements;
        this.totalPages = data.totalPages;
        this.currentPage = page;
        this.mostraRisultati = false; // Assicurati che mostri i risultati di getLuoghi
      } else {
        console.error("I dati ricevuti non sono un array", data);
      }
    },
    (error: HttpErrorResponse) => {
      console.error('Errore nella richiesta HTTP:', error);
    });
  }

  cercaLuogo(tipoRicerca: string): void {
    if (this.titoloDaCercare.trim() !== '') {
      switch (tipoRicerca) {
        case 'titolo':
          this.homeSrv.getLuogoByTitolo(this.titoloDaCercare).subscribe(
            (data: any) => {
              if (data && typeof data === 'object' && !Array.isArray(data)) {
                this.luoghiTrovati = [data];
                this.mostraRisultati = true;
              } else {
                console.error("I dati ricevuti non sono validi", data);
              }
            },
            (error: HttpErrorResponse) => {
              console.error('Errore nella richiesta HTTP:', error);
            }
          );
          break;
        case 'descrizione':
          this.homeSrv.getLuogoByDescrizione(this.titoloDaCercare).subscribe(
            (data: any) => {
              if (Array.isArray(data)) {
                this.luoghiTrovati = data;
                this.mostraRisultati = true;
              } else {
                console.error("I dati ricevuti non sono validi", data);
              }
            },
            (error: HttpErrorResponse) => {
              console.error('Errore nella richiesta HTTP:', error);
            }
          );
          break;
        case 'prodotto':
          this.homeSrv.getLuogoByNomeProdotto(this.titoloDaCercare).subscribe(
            (data: any) => {
              if (Array.isArray(data)) {
                this.luoghiTrovati = data;
                this.mostraRisultati = true;
              } else {
                console.error("I dati ricevuti non sono validi", data);
              }
            },
            (error: HttpErrorResponse) => {
              console.error('Errore nella richiesta HTTP:', error);
            }
          );
          break;
        default:
          console.error("Tipo di ricerca non valido");
          break;
      }
    }
  }

  creaLuogo(luogo: any) {
    this.homeSrv.createLuogo(luogo).subscribe(
      (response) => {
        console.log('Luogo creato con successo', response);
      },
      (error) => {
        console.error('Errore durante la creazione del luogo', error);
      }
    );
  }

  aggiornaLuogo(id: string, nuovoLuogo: any) {
    this.homeSrv.updateLuogo(id, nuovoLuogo).subscribe(
      (response) => {
        console.log('Luogo aggiornato con successo', response);
      },
      (error) => {
        console.error('Errore durante l\'aggiornamento del luogo', error);
      }
    );
  }

  eliminaLuogo(id: string) {
    this.homeSrv.deleteLuogo(id).subscribe(
      () => {
        console.log('Luogo eliminato con successo');
      },
      (error) => {
        console.error('Errore durante l\'eliminazione del luogo', error);
      }
    );
  }

  mostraFormCreazione(): void {
    this.mostraForm = true;
  }

  mostraFormAggiornamento(luogo: any): void {
    this.mostraForm = true;
    this.formLuogo = { ...luogo };
  }

  inviaForm(): void {
    if (this.formLuogo.id) {
      // Esegui l'aggiornamento
      this.aggiornaLuogo(this.formLuogo.id, this.formLuogo);
    } else {
      // Esegui la creazione
      this.creaLuogo(this.formLuogo);
    }
    this.mostraForm = false;
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.getLuoghi(this.currentPage + 1);
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.getLuoghi(this.currentPage - 1);
    }
  }
}
