import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.scss']
})
export class ProfiloComponent implements OnInit {
  userPhotoUrl!: SafeUrl | null;

  constructor(private domSan: DomSanitizer) { }

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
  }

}
