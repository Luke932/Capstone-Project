import { Component, OnInit } from '@angular/core';
import { FooterService } from 'src/app/services/footer.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {
  showFooter = true;
  constructor(private footSrv: FooterService) { }

  get showFooters(): boolean {
    return this.footSrv.getShowFooter();
  }

  ngOnInit(): void {
  }

}
