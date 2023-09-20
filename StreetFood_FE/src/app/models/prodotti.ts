import { Commento } from "./commento";

export interface Prodotti {
  mostraFormCommento: boolean;
  id?: string,
  nomeProdotto: string;
  descrizione: string;
  immagine: string;
  altro: string;
  isLiked: boolean;
likeId?: string;
commenti?: Commento[];
}
