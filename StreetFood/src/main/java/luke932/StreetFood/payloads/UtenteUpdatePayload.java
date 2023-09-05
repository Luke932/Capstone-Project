package luke932.StreetFood.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import luke932.StreetFood.entities.Ruolo;

@Getter
@Setter
@AllArgsConstructor
public class UtenteUpdatePayload {

	private String username;
	private String password;
	private String email;
	private String nome;
	private String cognome;

	private Ruolo ruolo;

}
