package luke932.StreetFood.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UtenteSavePayloadUser {

	private String nome;
	private String cognome;
	private String username;
	private String email;
	private String password;
}