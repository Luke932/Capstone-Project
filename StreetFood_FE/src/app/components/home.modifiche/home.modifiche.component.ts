import { Component, OnInit } from '@angular/core';
import { FooterService } from 'src/app/services/footer.service';

@Component({
  selector: 'app-home.modifiche',
  templateUrl: './home.modifiche.component.html',
  styleUrls: ['./home.modifiche.component.scss']
})
export class HomeModificheComponent implements OnInit {

  constructor(private footSrv: FooterService) {
    this.footSrv.setShowFooter(true);
  }

  ngOnInit(): void {
  }

}
