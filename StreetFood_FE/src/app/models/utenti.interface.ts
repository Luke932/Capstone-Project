export interface UtentiInterface {
  id?: string;
  email: string;
  password: string;
  nome: string;
  cognome: string;
  username: string;
  foto: string;
  ruolo: {
    nome: string;
  };
  nomeRuolo: string;
}
