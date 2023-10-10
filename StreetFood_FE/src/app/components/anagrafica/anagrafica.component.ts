import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Utente } from "src/app/models/utente.interface";
import { ProdottiService } from "src/app/services/prodotti.service";
import { ProfiloService } from "src/app/services/profilo.service";
import { SharedDataService } from "src/app/services/shared-data.service";
import { UtenteService } from "src/app/services/utente.service";

@Component({
  selector: "app-anagrafica",
  templateUrl: "./anagrafica.component.html",
  styleUrls: ["./anagrafica.component.scss"],
})
export class AnagraficaComponent implements OnInit {
  utente: Utente[] = [];
  utenteForm!: FormGroup;
  utenteId: string = "";

  constructor(
    private utenteSrv: UtenteService,
    private fb: FormBuilder,
    private prodottiSrv: ProdottiService,
    private profiloSrv: ProfiloService,
    private sharedDataService: SharedDataService
  ) {
    this.utenteForm = this.fb.group({
      username: ["", Validators.required],
      nome: ["", Validators.required],
      cognome: ["", Validators.required],
      email: ["", [Validators.required, Validators.email]],
      password: [
        "",
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/
          ),
        ],
      ],
    });

    this.sharedDataService.reloadProfilePage$.subscribe(() => {
      this.reloadProfilePage();
    });
  }

  ngOnInit(): void {
    this.utenteId = this.prodottiSrv.getId() || "";

    this.profiloSrv.getUserById(this.utenteId).subscribe(
      (utente: any) => {
        this.utenteForm.patchValue({
          username: utente.username,
          nome: utente.nome,
          cognome: utente.cognome,
          email: utente.email,
          password: "",
        });
      },
      (error) => {
        console.error("Errore durante il recupero dell'utente:", error);
      }
    );
  }

  aggiornaProdotto(id: string, nuovoUtente: any) {
    this.utenteSrv.updateUser(id, nuovoUtente).subscribe(
      (response) => {
        const indice = this.utente.findIndex((lg) => lg.id === id);
        if (indice !== -1) {
          this.utente[indice] = response;
        }
        console.log("Utente aggiornato con successo", response);
      },
      (error) => {
        if (error.status === 404) {
          console.error("Utente non trovato con ID:", id);
        } else {
          console.error("Errore durante l'aggiornamento dell'utente", error);
        }
      }
    );
  }

  onSubmit() {
    const nuovoUtente = this.utenteForm.value;
    const idUtente = this.utenteId;

    this.utenteSrv.updateUser(idUtente, nuovoUtente).subscribe(
      (response) => {
        console.log("Utente aggiornato con successo", response);
        this.sharedDataService.triggerProfileReload();
      },
      (error) => {
        if (error.status === 404) {
          console.error("Utente non trovato con ID:", idUtente);
        } else {
          console.error("Errore durante l'aggiornamento dell'utente", error);
        }
      }
    );
  }

  reloadProfilePage() {
    window.location.reload();
  }
}
