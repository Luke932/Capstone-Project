import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Luoghi } from 'src/app/models/luoghi';
import { Prodotti } from 'src/app/models/prodotti';
import { HomeServiceService } from 'src/app/services/home.service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  luoghi: Luoghi[] = [];
  constructor(private homeSrv: HomeServiceService) { }

  ngOnInit(): void {
    this.homeSrv.getAllLuoghi().subscribe((data : any) => {
      if(Array.isArray(data.content)) {
        this.luoghi = data.content;
        console.log(this.luoghi);
      } else {
        console.error("I dati ricevuti non sono un array", data);
      }
    },
    (error: HttpErrorResponse) => {
      console.error('Errore nella richiesta HTTP:', error);
    }
    );
  }
}
