import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms'
import { AuthService } from '../auth.service.service';
import { Router } from '@angular/router'
import { AuthData } from '../auth-data.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    user:AuthData | undefined;
    token:string | undefined;
  isLoading = false

  constructor(private authServ:AuthService, private router:Router) { }

  ngOnInit(): void {
  }

  access(form:NgForm){
    this.isLoading = true
    console.log(form.value)
    try {
      this.authServ.login(form.value).subscribe(
        () => {
          this.router.navigate(['/home']);
          this.isLoading = false;
        },
        (error) => {
          console.error(error.error);
          console.log("Errore")
          console.log(5);
          if (error.error === 'Incorrect password') {
            alert('Occhio, pirata! Hai sbagliato password!');
          }else if(error.error === 'Cannot find user'){
            alert('Registrati!')
          }
          this.isLoading = false;
        }
      );
    } catch (error: any) {
      console.error(error);
      this.isLoading = false;
      console.log(this.user)
    }



  }
}
