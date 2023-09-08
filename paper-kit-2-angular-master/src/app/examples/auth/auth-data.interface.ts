

export interface AuthData {

  token: string;
  utente: {
    password: string;
    email: string;
    nome: string;
    cognome: string;
    username: string;
    id:string
  };
}