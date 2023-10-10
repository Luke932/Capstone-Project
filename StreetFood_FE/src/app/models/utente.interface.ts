export interface Utente {
  email:string,
  password:string,
  nome:string,
  cognome:string,
  username:string,
  immagine: string| any,
  ruolo?:string,
  id?:string
}
