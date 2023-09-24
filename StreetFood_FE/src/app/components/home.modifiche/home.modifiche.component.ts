import { HttpErrorResponse } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { Luoghi } from "src/app/models/luoghi";
import { FooterService } from "src/app/services/footer.service";
import { HomeServiceService } from "src/app/services/home.service.service";
import { ChangeDetectorRef } from "@angular/core";

@Component({
  selector: "app-home.modifiche",
  templateUrl: "./home.modifiche.component.html",
  styleUrls: ["./home.modifiche.component.scss"],
})
export class HomeModificheComponent implements OnInit {
  luoghi: Luoghi[] = [];
  luoghiTrovati: Luoghi[] = [];
  currentPage: number = 0;
  totalPages: number = 0;
  totalElements: number = 0;
  titoloDaCercare: string = "";
  mostraRisultati: boolean = false;
  tipoRicerca: string = "descrizione";
  mostraForm: boolean = false;
  formLuogo: any = {};
  luogoDaNascondere: string = "";
  mostraMessaggio: boolean = false;

  constructor(
    private homeSrv: HomeServiceService,
    private footSrv: FooterService,
    private domSan: DomSanitizer,
    private cdr: ChangeDetectorRef
  ) {
    this.footSrv.setShowFooter(true);
  }

  ngOnInit(): void {
    this.getLuoghi(0);
  }

  getLuoghi(page: number): void {
    this.homeSrv.getAllLuoghi(page).subscribe(
      (data: any) => {
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
        console.error("Errore nella richiesta HTTP:", error);
      }
    );
  }

  cercaLuogo(tipoRicerca: string): void {
    if (this.titoloDaCercare.trim() !== "") {
      switch (tipoRicerca) {
        case "titolo":
          this.homeSrv.getLuogoByTitolo(this.titoloDaCercare).subscribe(
            (data: any) => {
              if (data && typeof data === "object" && !Array.isArray(data)) {
                this.luoghiTrovati = [data];
                this.mostraRisultati = true;
                this.mostraMessaggio = false;
              } else {
                console.error("I dati ricevuti non sono validi", data);
                this.mostraRisultati = false;
                this.mostraMessaggio = true; // Aggiunto
              }
            },
            (error: HttpErrorResponse) => {
              console.error("Errore nella richiesta HTTP:", error);
              this.mostraRisultati = false;
              this.mostraMessaggio = true; // Aggiunto
            }
          );
          break;
        case "descrizione":
          this.homeSrv.getLuogoByDescrizione(this.titoloDaCercare).subscribe(
            (data: any) => {
              if (Array.isArray(data)) {
                this.luoghiTrovati = data;
                this.mostraRisultati = true;
                this.mostraMessaggio = false;
              } else {
                console.error("I dati ricevuti non sono validi", data);
                this.mostraRisultati = false;
                this.mostraMessaggio = true; // Aggiunto
              }
            },
            (error: HttpErrorResponse) => {
              console.error("Errore nella richiesta HTTP:", error);
              this.mostraRisultati = false;
              this.mostraMessaggio = true; // Aggiunto
            }
          );
          break;
        case "prodotto":
          this.homeSrv.getLuogoByNomeProdotto(this.titoloDaCercare).subscribe(
            (data: any) => {
              if (Array.isArray(data)) {
                this.luoghiTrovati = data;
                this.mostraRisultati = true;
                this.mostraMessaggio = false;
              } else {
                console.error("I dati ricevuti non sono validi", data);
                this.mostraRisultati = false;
                this.mostraMessaggio = true; // Aggiunto
              }
            },
            (error: HttpErrorResponse) => {
              console.error("Errore nella richiesta HTTP:", error);
              this.mostraRisultati = false;
              this.mostraMessaggio = true; // Aggiunto
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
        this.luoghi.push(response);
        console.log("Luogo creato con successo", response);
      },
      (error) => {
        console.error("Errore durante la creazione del luogo", error);
      }
    );
  }

  aggiornaLuogo(id: string, nuovoLuogo: any) {
    this.homeSrv.updateLuogo(id, nuovoLuogo).subscribe(
      (response) => {
        const indice = this.luoghi.findIndex((lg) => lg.id === id);
        if (indice !== -1) {
          this.luoghi[indice] = response; // Aggiorna l'oggetto Luogo con la risposta dal backend
        }
        console.log("Luogo aggiornato con successo", response);
      },
      (error) => {
        if (error.status === 404) {
          console.error("Luogo non trovato con ID:", id);
        } else {
          console.error("Errore durante l'aggiornamento del luogo", error);
        }
      }
    );
  }

  eliminaLuogo(id: string) {
    this.homeSrv.deleteLuogo(id).subscribe(
      () => {
        this.luoghi = this.luoghi.filter((lg) => lg.id !== id);
        console.log("Luogo eliminato con successo");
      },
      (error) => {
        console.error("Errore durante l'eliminazione del luogo", error);
      }
    );
  }

  mostraFormCreazione(): void {
    this.mostraForm = true;
    this.formLuogo = {};
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
