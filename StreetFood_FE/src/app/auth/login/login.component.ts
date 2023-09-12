import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../auth.service.service';
import { Router } from '@angular/router';
import { RoleService } from '../role.service';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  isLoading = false;
  userRole$ = new BehaviorSubject<string>('');
  usernameUser = new BehaviorSubject<string>('');

  constructor(private authService: AuthService, private router: Router, private roleSrv: RoleService) {}

  ngOnInit(): void {}

  access(form: NgForm) {
    this.isLoading = true;

    this.authService.login(form.value).subscribe(
      (response: any) => {
        const token = response.token;
        if (token) {
          localStorage.setItem('token', token);

          // Ora che abbiamo il token, otteniamo i dettagli dell'utente
          this.authService.getUserDetails().subscribe(
            (user) => {
              console.log('Dettagli utente:', user);
              const userRole = user?.ruolo?.nome;
              const username = user?.username;
              this.authService.setUserProfile(username);
              this.usernameUser.next(username);

              console.log('Ruolo dell\'utente:', userRole);

              this.roleSrv.setUserRole(userRole);
              this.userRole$.next(userRole);

              if (userRole === 'ADMIN') {
                this.router.navigate(['/admin']);
              } else if (userRole === 'USER') {
                this.router.navigate(['/user']);
              } else {
                console.error('Ruolo non gestito:', userRole);
              }

              this.isLoading = false;
            },
            (error) => {
              console.error('Errore nel recuperare i dettagli dell\'utente:', error);
              this.isLoading = false;
            }
          );
        } else {
          console.error('Il server non ha restituito un token.');
          this.isLoading = false;
        }
      },
      (error) => {
        console.error(error);

        if (error.error === 'Incorrect password') {
          alert('Occhio, pirata! Hai sbagliato password!');
        } else if (error.error === 'Cannot find user') {
          alert('Registrati!');
        } else {
          alert('Errore sconosciuto durante il login. Controlla la console per i dettagli.');
        }

        this.isLoading = false;
      }
    );
  }
}
