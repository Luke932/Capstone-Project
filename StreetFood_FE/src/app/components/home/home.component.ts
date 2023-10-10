import { HttpErrorResponse } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { Luoghi } from "src/app/models/luoghi";
import { FooterService } from "src/app/services/footer.service";
import { HomeServiceService } from "src/app/services/home.service.service";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"],
})
export class HomeComponent implements OnInit {
  luoghi: Luoghi[] = [];
  luoghiTrovati: Luoghi[] = [];
  currentPage: number = 0;
  totalPages: number = 0;
  totalElements: number = 0;
  titoloDaCercare: string = "";
  mostraRisultati: boolean = false;
  tipoRicerca: string = "descrizione";
  mostraMessaggio: boolean = false;

  constructor(
    private homeSrv: HomeServiceService,
    private footSrv: FooterService
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
          this.mostraRisultati = false;
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
                this.mostraMessaggio = true;
              }
            },
            (error: HttpErrorResponse) => {
              console.error("Errore nella richiesta HTTP:", error);
              this.mostraRisultati = false;
              this.mostraMessaggio = true;
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
                this.mostraMessaggio = true;
              }
            },
            (error: HttpErrorResponse) => {
              console.error("Errore nella richiesta HTTP:", error);
              this.mostraRisultati = false;
              this.mostraMessaggio = true;
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
                this.mostraMessaggio = true;
              }
            },
            (error: HttpErrorResponse) => {
              console.error("Errore nella richiesta HTTP:", error);
              this.mostraRisultati = false;
              this.mostraMessaggio = true;
            }
          );
          break;
        default:
          console.error("Tipo di ricerca non valido");
          this.mostraMessaggio = true;
          break;
      }
    }
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
