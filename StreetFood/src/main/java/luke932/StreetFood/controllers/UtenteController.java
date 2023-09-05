package luke932.StreetFood.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.payloads.UtenteUpdatePayload;
import luke932.StreetFood.services.UtenteService;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
	private final UtenteService utenteService;

	public UtenteController(UtenteService utenteService) {
		this.utenteService = utenteService;
	}

	// --------------- POST UTENTE
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Utente saveUser(@RequestBody Utente body) {
		Utente created = utenteService.save(body);
		return created;
	}

	// --------------- GET UTENTI
	@GetMapping
	public List<Utente> getUsers() {
		return utenteService.findNoPage();
	}

	// --------------- GET UTENTE BY ID
	@GetMapping("/{userId}")
	public Utente getUserById(@PathVariable UUID userId) {
		return utenteService.findById(userId);

	}

	// --------------- PUT UTENTE
	@PutMapping("/{userId}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Utente updateUser(@PathVariable UUID userId, @RequestBody UtenteUpdatePayload body) {
		return utenteService.updateUtente(userId, body);
	}

	// --------------- DELETE UTENTE
	@DeleteMapping("/{userId}")
	// @PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		utenteService.deleteUtente(userId);
	}

}