import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  showNavbar = false;



  constructor(private authService: AuthService) {
    this.authService.user$.subscribe(user => {
      this.showNavbar = !!user;
    });
  }

  ngOnInit(): void {
    this.authService.restore();
  }




  title = 'StreetFood_FE';
}
