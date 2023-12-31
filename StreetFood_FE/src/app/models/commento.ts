import { Prodotti } from "./prodotti";


export interface Commento {
  id: string;
  utente: string | undefined; // Assumi che esista anche un'interfaccia Utente
  prodotto: Prodotti; // Assumi che esista anche un'interfaccia Prodotto
  testoCommento: string;
  dataCommento: Date;
}
