package luke932.StreetFood.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import luke932.StreetFood.entities.Ruolo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtenteUpdatePayload {

	private String username;
	private String password;
	private String email;
	private String nome;
	private String cognome;

	private Ruolo ruolo;

	public UtenteUpdatePayload(String username, String password, String email, String nome, String cognome) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
	}

}
