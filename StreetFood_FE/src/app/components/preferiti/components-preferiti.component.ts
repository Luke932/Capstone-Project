import { Component, OnInit } from '@angular/core';
import { LikeService } from 'src/app/services/like.service';
import { ProdottiService } from 'src/app/services/prodotti.service';

@Component({
  selector: 'app-preferiti',
  templateUrl: './components-preferiti.component.html',
  styleUrls: ['./components-preferiti.component.scss']
})
export class PreferitiComponent implements OnInit {
  utenteId: string = "";
  prodottiConLike: any[] | undefined;

  constructor(private prodottiSrv: ProdottiService, private likeSrv: LikeService) { }

  ngOnInit(): void {
    this.utenteId = this.prodottiSrv.getId() || "";
    this.getProductWithLikes(this.utenteId);
  }


  getProductWithLikes(utenteId: string) {
    this.likeSrv.getProdottiConLikeDaUtente(utenteId).subscribe(data => {
      this.prodottiConLike = data;
    });
  }

}
