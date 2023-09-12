package luke932.StreetFood.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.exceptions.BadRequestException;
import luke932.StreetFood.exceptions.UnauthorizedException;
import luke932.StreetFood.payloads.UtenteLoginPayload;
import luke932.StreetFood.payloads.UtenteLoginSuccessful;
import luke932.StreetFood.payloads.UtenteSavePayloadUser;
import luke932.StreetFood.services.UtenteService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UtenteService utenteSrv;
	private final JWTTools jwtTools;
	private final PasswordEncoder bcrypt;

	@Autowired
	public AuthController(UtenteService utenteSrv, JWTTools jwtTools, PasswordEncoder bcrypt) {
		this.utenteSrv = utenteSrv;
		this.jwtTools = jwtTools;
		this.bcrypt = bcrypt;
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public Utente saveUser(@RequestBody @Validated UtenteSavePayloadUser body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		Utente created = utenteSrv.createUser(body);

		return created;
	}

	@PostMapping("/login")
	public ResponseEntity<UtenteLoginSuccessful> login(@RequestBody UtenteLoginPayload body)
			throws UnauthorizedException {

		Utente user = utenteSrv.findByEmail(body.getEmail());

		if (bcrypt.matches(body.getPassword(), user.getPassword())) {

			String token = jwtTools.createToken(user);

			UtenteLoginSuccessful loginAvvenuto = new UtenteLoginSuccessful(token);
			return new ResponseEntity<>(loginAvvenuto, HttpStatus.OK);

		} else {

			throw new UnauthorizedException("Credenziali non valide!");
		}
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUtente(@PathVariable UUID id) throws BadRequestException {
		utenteSrv.deleteUtente(id);

	}

}