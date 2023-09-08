package luke932.StreetFood.payloads;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UtenteLoginSuccessful {
	private String token;

	public UtenteLoginSuccessful(String token) {

		this.token = token;
	}

	@Override
	public String toString() {
		return "UtenteLoginSuccessful [token=" + token + "]";
	}

}
