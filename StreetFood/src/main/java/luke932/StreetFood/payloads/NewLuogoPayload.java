package luke932.StreetFood.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewLuogoPayload {

	private String titolo;
	private byte[] immagine;
	private String descrizione;
}
