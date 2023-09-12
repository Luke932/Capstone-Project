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

  constructor(private roleSrv: RoleService, private router: Router, private authSrv: AuthService) {}

  ngOnInit(): void {
    this.userRole$ = this.roleSrv.getUserRole$();
    console.log(this.userRole$);
   /* this.authSrv.getUserProfile().pipe(take(1)).subscribe(username => {
      this.username = of(username);
      console.log(this.username);
    });*/
    this.authSrv.getUserProfile().subscribe(userProfile => {
      this.username = userProfile;
      console.log(userProfile);
    });

  }

}
