package luke932.StreetFood.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import luke932.StreetFood.entities.Ruolo;

@Setter
@Getter
@AllArgsConstructor
public class UtenteSavePayloadUser {

	@NotNull(message = "Nome obbligatorio")
	private String nome;
	@NotNull(message = "Cognome obbligatorio")
	private String cognome;
	@NotNull(message = "Username obbligatorio")
	private String username;
	@NotNull(message = "Email è obbligatoria")
	@Email(message = "Formato e-mail errato")
	private String email;
	@NotNull(message = "Password obbligatoria")
	private String password;
	private byte[] foto;
	private Ruolo ruolo;

	public UtenteSavePayloadUser(@NotNull(message = "Nome obbligatorio") String nome,
			@NotNull(message = "Cognome obbligatorio") String cognome,
			@NotNull(message = "Username obbligatorio") String username,
			@NotNull(message = "Email è obbligatoria") @Email(message = "Formato e-mail errato") String email,
			@NotNull(message = "Password obbligatoria") String password, Ruolo ruolo) {
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.email = email;
		this.password = password;
		this.ruolo = ruolo;
	}

}
