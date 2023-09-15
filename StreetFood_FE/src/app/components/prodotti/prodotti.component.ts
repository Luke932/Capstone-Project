import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
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

  ngOnInit(): void {
  this.getProdotti(0);
  }

  getProdotti(page: number): void {
    this.prodottiSrv.getAllProdotti(page).subscribe((data: any) => {
      if (Array.isArray(data.content)) {
        this.prodotti = data.content;
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

}
