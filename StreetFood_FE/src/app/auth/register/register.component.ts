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
  selectedFile!: File;

  constructor(private authSrv: AuthService, private router:Router, private footSrv: FooterService, private app: AppComponent) {
    this.footSrv.setShowFooter(false);
  }

  ngOnInit(): void {
    this.app.showNavbar = false;
  }

  onFileSelected(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement?.files && inputElement.files[0]) {
      this.selectedFile = inputElement.files[0];
    }
  }


  registra(form: NgForm) {
    this.isLoading = true;

    try {
      const formData = new FormData();
      formData.append('username', form.value.username);
      formData.append('nome', form.value.nome);
      formData.append('cognome', form.value.cognome);
      formData.append('email', form.value.email);
      formData.append('password', form.value.password);
      formData.append('file', this.selectedFile);

      this.authSrv.signup(formData).subscribe(
        () => {
          this.router.navigate(['/login']);
          this.isLoading = false;
        },
        (error) => {
          console.error(error.error);
          if (error.error === 'Email format is invalid') {
            alert('Formato email non valido!');
          } else if (error.error === 'Email already exists') {
            alert('Email già in uso!');
          } else if (error.error === 'Password is too short') {
            alert('Password troppo corta!');
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
