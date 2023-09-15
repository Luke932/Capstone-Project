export interface AuthData {
  [x: string]: any;

  token: string;
  utente: {
    password: string;
    email: string;
    nome: string;
    cognome: string;
    username: string;
    immagine: string;
    id:string,
    ruolo:string
  };
}
