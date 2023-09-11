import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service.service';
import { UtenteService } from 'src/app/services/utente.service';

@Component({
  selector: 'app-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.scss']
})
export class ProfiloComponent implements OnInit {

  constructor(private UtenteSrv: UtenteService, private authSrv: AuthService) { }

  ngOnInit(): void {
    this.UtenteSrv.getAllUtenti().subscribe(
      (data: any) => {
        // Verifica se data è un array prima di assegnarlo a clienti
        if (Array.isArray(data.content)) {
          this.UtenteSrv = data.content;
          console.log(this.UtenteSrv);
        } else {
          console.error('I dati ricevuti non sono un array:', data);
        }
      },
      (error: HttpErrorResponse) => {
        console.error('Errore nella richiesta HTTP:', error);
      }
    );
  }

}
