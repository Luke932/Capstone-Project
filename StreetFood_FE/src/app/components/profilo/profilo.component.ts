import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { LikeService } from 'src/app/services/like.service';
import { ProdottiService } from 'src/app/services/prodotti.service';
import { ProfiloService } from 'src/app/services/profilo.service';


@Component({
  selector: 'app-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.scss']
})
export class ProfiloComponent implements OnInit {
  userPhotoUrl!: SafeUrl | null;
  selectedRoute: string = '';


  constructor(private domSan: DomSanitizer,private profiloService: ProfiloService) { }

  ngOnInit(): void {
    const imageByte = localStorage.getItem('userPhotoUrl');
    console.log(imageByte);

    if (imageByte) {
      const byteCharacters = atob(imageByte);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/jpeg' });

      this.userPhotoUrl = this.domSan.bypassSecurityTrustUrl(URL.createObjectURL(blob));
    }

    const savedRoute = localStorage.getItem('selectedRoute');

    if (savedRoute) {
      this.selectedRoute = savedRoute;
    }

  }

  handleClick(route: string) {
    this.selectedRoute = route;
    localStorage.setItem('selectedRoute', route);
  }


}
