import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, take } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service.service';
import { RoleService } from 'src/app/auth/role.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  userRole$!: Observable<string>;
  username!: string;
  isLoggedIn = false;

  constructor(private roleSrv: RoleService, private router: Router, private authSrv: AuthService) {}

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
      }
    });
  }




}
