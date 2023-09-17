import { Prodotti } from "./prodotti";
import { Utente } from "./utente.interface";

export interface Like {
  id?: string;
  prodotto: Prodotti;
  utente: Utente;
  dataLike: Date;
}
