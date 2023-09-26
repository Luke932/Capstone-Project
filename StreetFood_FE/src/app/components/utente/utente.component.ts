import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { UtentiInterface } from 'src/app/models/utenti.interface';
import { UtenteService } from 'src/app/services/utente.service';

@Component({
  selector: 'app-utente',
  templateUrl: './utente.component.html',
  styleUrls: ['./utente.component.scss']
})
export class UtenteComponent implements OnInit {
  utenti: UtentiInterface[] = [];
  selectedFile!: File;
  utenteForm!: FormGroup;
  utenteId: string = "";
  showForm: boolean = false;
  isNewUser: boolean = false;
  selectedRuolo!: string;

  constructor(private utenteSrv: UtenteService, private domSan: DomSanitizer, private fb: FormBuilder) {
    this.utenteForm = this.fb.group({
      username: ["", Validators.required],
      nome: ["", Validators.required],
      cognome: ["", Validators.required],
      email: ["", [Validators.required, Validators.email]],
      password: [
        "",
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/
          ),
        ],
      ],
    });
  }

  ngOnInit(): void {
    this.getAllUtenti();
  }

  getAllUtenti() {
    this.utenteSrv.getAllUtenti().subscribe(
      utenti => {
        console.log(utenti);
        this.utenti = utenti;
      },
      error => {
        console.error('Errore durante il recupero degli utenti:', error);
      }
    );
  }

  convertByteArrayToImageUrl(imageByte: string): any {
    if (imageByte) {
      const byteCharacters = atob(imageByte);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/jpeg' });
      const image = this.domSan.bypassSecurityTrustUrl(URL.createObjectURL(blob));
      return image;
    } else {
      return null;
    }
  }

  onFileSelected(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement?.files && inputElement.files[0]) {
      this.selectedFile = inputElement.files[0];
    }
  }

  showEditForm(utente: UtentiInterface) {
    this.showForm = true;
    this.isNewUser = false;
    this.utenteId = utente.id ? utente.id : "";
    this.selectedRuolo = utente.ruolo.nome; // Aggiungi questa riga

    this.utenteForm.setValue({
      username: utente.username,
      nome: utente.nome,
      cognome: utente.cognome,
      email: utente.email,
      password: "", // Assicurati che il campo password sia vuoto
    });
  }



  showCreateForm() {
    this.utenteForm.reset(); // Resetta il form
    this.showForm = true; // Mostra il form
    this.isNewUser = true;
    this.utenteId = ""; // Resetta l'ID dell'utente
  }

  updateUtente(idUtente: string) {
    try {
      const formData = new FormData();
      formData.append('username', this.utenteForm.get('username')!.value);
      formData.append('nome', this.utenteForm.get('nome')!.value);
      formData.append('cognome', this.utenteForm.get('cognome')!.value);
      formData.append('email', this.utenteForm.get('email')!.value);
      formData.append('password', this.utenteForm.get('password')!.value);
      formData.append('ruolo', this.utenteForm.get('ruolo')!.value.nome);
      formData.append('file', this.selectedFile);

      this.utenteSrv.updateUser(idUtente, formData).subscribe(
        response => {
          console.log('Utente aggiornato con successo', response);
          this.getAllUtenti();
        },
        error => {
          console.error('Errore durante l\'aggiornamento dell\'utente', error);
        }
      );
    } catch (error) {
      console.error('Errore durante l\'aggiornamento dell\'utente', error);
    }
  }

  createUtenteUser(form: NgForm) {
      const formData = new FormData();
      formData.append('username', form.value.username);
      formData.append('nome', form.value.nome);
      formData.append('cognome', form.value.cognome);
      formData.append('email', form.value.email);
      formData.append('password', form.value.password);
      formData.append('file', this.selectedFile);


      this.utenteSrv.registerUserWithFile(formData).subscribe(
        response => {
          console.log('Utente creato con successo', response);
          this.getAllUtenti();
          this.cancelEdit();
        },
        error => {
          console.error('Errore durante la creazione dell\'utente', error);
        }
      );
  }

  createUtenteAdmin(form: NgForm) {
    const formData = new FormData();
    formData.append('username', form.value.username);
    formData.append('nome', form.value.nome);
    formData.append('cognome', form.value.cognome);
    formData.append('email', form.value.email);
    formData.append('password', form.value.password);
    formData.append('file', this.selectedFile);


    this.utenteSrv.saveUserWithFile(formData).subscribe(
      response => {
        console.log('Admin creato con successo', response);
        this.getAllUtenti();
        this.cancelEdit();
      },
      error => {
        console.error('Errore durante la creazione dell\'utente', error);
      }
    );
}

  saveOrUpdateUtente(form: NgForm) {
    const idUtente = this.utenteId;

    if (idUtente) {
      this.updateUtente(idUtente);
    } else {
      this.createUtenteUser(form);
    }
  }




  deleteUser(id: string) {
    this.utenteSrv.deleteUser(id).subscribe(
      response => {
        console.log('Utente eliminato con successo');
        this.getAllUtenti();
      },
      error => {
        console.error('Errore durante l\'eliminazione dell\'utente', error);
      }
    );
  }




  cancelEdit() {
    this.showForm = false; // Nasconde il form
    this.utenteForm.reset(); // Resetta il form
    this.utenteId = ""; // Resetta l'ID dell'utente
  }
}
  function saveOrUpdateUtente() {
    throw new Error('Function not implemented.');
  }

