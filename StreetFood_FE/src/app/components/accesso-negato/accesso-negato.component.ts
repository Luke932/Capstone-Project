import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { FooterService } from 'src/app/services/footer.service';

@Component({
  selector: 'app-accesso-negato',
  templateUrl: './accesso-negato.component.html',
  styleUrls: ['./accesso-negato.component.scss']
})
export class AccessoNegatoComponent implements OnInit {
  isLoggedIn = false;

  constructor(private app: AppComponent, private footSrv: FooterService) {
    this.footSrv.setShowFooter(false);
    this.app.showNavbar = false;
   }

  ngOnInit(): void {

  }

}
