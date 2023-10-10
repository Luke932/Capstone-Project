import { Prodotti } from "./prodotti";
import { Utente } from "./utente.interface";

export interface Like {
  id?: string;
  prodottoId: string | undefined;
  utente: string | undefined;
  dataLike: Date;
}
