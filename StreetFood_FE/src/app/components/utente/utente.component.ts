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
  updateForm!: FormGroup;
  showUpdateForm = false;
  updateFormRef: any;

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

    this.updateForm = this.fb.group({
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
      nomeRuolo: ["", Validators.required],
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



  showUpdateForms(utente: UtentiInterface) {
    console.log(utente);

    this.showForm = false;
    this.showUpdateForm = true;
    this.isNewUser = false;
    this.utenteId = utente.id ? utente.id : "";
    this.selectedRuolo = utente.ruolo.nome;

    this.utenteForm.patchValue({
      username: utente.username,
      nome: utente.nome,
      cognome: utente.cognome,
      email: utente.email,
      password: "",
      nomeRuolo: utente.ruolo.nome,
    });
  }

  showCreateForm() {
    this.utenteForm.reset(); // Resetta il form
    this.showForm = true; // Mostra il form
    this.isNewUser = true;
    this.utenteId = ""; // Resetta l'ID dell'utente
  }


  updateUtente() {
    console.log('updateUtente() chiamata');
    const updateData = this.updateForm.value;
    const idUtente = this.utenteId;

    try {
      console.log('Dati di aggiornamento:', updateData); // Aggiunto
      console.log('ID Utente:', idUtente); // Aggiunto

      // Esegui l'aggiornamento utilizzando i dati
      this.utenteSrv.updateUser(idUtente, updateData).subscribe(
        response => {
          console.log('Utente aggiornato con successo', response);
          this.getAllUtenti();
          this.showUpdateForm = false;
          this.updateForm.reset();
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


  cancelUpdate() {
    this.showUpdateForm = false;
    // Resetta il form di aggiornamento
    this.updateForm.reset();
  }

  cancelEdit() {
    this.showForm = false; // Nasconde il form
    this.utenteForm.reset(); // Resetta il form
    this.utenteId = ""; // Resetta l'ID dell'utente
  }
}

