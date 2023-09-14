import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, take } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service.service';
import { RoleService } from 'src/app/auth/role.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  userRole$!: Observable<string>;
  username!: string;
  isLoggedIn = false;
  userPhotoUrl!: SafeUrl | null;


  constructor(private roleSrv: RoleService, private router: Router, private authSrv: AuthService, private domSan: DomSanitizer) {}

  ngOnInit(): void {
    this.userRole$ = this.roleSrv.getUserRole$();
    this.authSrv.user$.subscribe(user => {
      this.isLoggedIn = !!user;

      if (this.isLoggedIn) {
        const username = localStorage.getItem('username');
        if (username) {
          this.username = username;
        } else {
          this.authSrv.getUserProfile().subscribe(username => {
            if (username) {
              console.log(username);
              this.username = username;
            }
          });
        }

        const imageByte = localStorage.getItem('userPhotoUrl');
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

        console.log(this.userPhotoUrl);

      }
    });
  }





}
