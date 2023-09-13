import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service.service';
import { Router} from '@angular/router'
import { NgForm} from '@angular/forms'
import { FooterService } from 'src/app/services/footer.service';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  isLoading = false

  constructor(private authSrv: AuthService, private router:Router, private footSrv: FooterService, private app: AppComponent) {
    this.footSrv.setShowFooter(false);
  }

  ngOnInit(): void {
    this.app.showNavbar = false;
  }

  registra(form: NgForm) {
    this.isLoading = true;
    console.log(form.value);
    try {
      this.authSrv.signup(form.value).subscribe(
        () => {
          this.router.navigate(['/login']);
          this.isLoading = false;
        },
        (error) => {
          console.error(error.error);
          if (error.error === 'Email format is invalid') {
            alert('Formato email non valido!');
          }
          else if(error.error === 'Email already exists'){
            alert('Email già in uso!')
          }
          else if(error.error === 'Password is too short'){
            alert('Password troppo corta!')
          }

          this.isLoading = false;
        }
      );
    } catch (error) {
      console.error(error);
      this.isLoading = false;
    }
  }

}
