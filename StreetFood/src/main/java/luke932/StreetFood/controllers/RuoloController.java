package luke932.StreetFood.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import luke932.StreetFood.entities.Ruolo;
import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.payloads.RuoloSavePayload;
import luke932.StreetFood.payloads.UtenteSavePayloadUser;
import luke932.StreetFood.services.RuoloService;
import luke932.StreetFood.services.UtenteService;

@RestController
@RequestMapping("/ruoli")
public class RuoloController {

	@Autowired
	private RuoloService ruoloService;

	@Autowired
	private UtenteService utenteSrv;

	// --------------- POST RUOLO
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public Ruolo saveRole(@RequestBody RuoloSavePayload body) {
		Ruolo created = ruoloService.save(body);
		return created;
	}

	// --------------- POST UTENTE ADMIN
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public Utente saveAdmin(@RequestBody UtenteSavePayloadUser body) {
		Utente created = utenteSrv.createAdminUser(body);
		return created;
	}

	// --------------- GET RUOLO
	@GetMapping
	public List<Ruolo> getRoles() {
		return ruoloService.findAll();
	}

	// --------------- GET RUOLO BY ID
	@GetMapping("/{ruolo}")
	public Ruolo getRoleById(@PathVariable UUID id) {
		return ruoloService.findById(id);

	}

	// --------------- PUT RUOLO
	@PutMapping("/{roleId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Ruolo updateRole(@PathVariable UUID roleId, @RequestBody Ruolo body) {
		return ruoloService.findByIdAndUpdate(roleId, body);
	}

	// --------------- DELETE RUOLO
	@DeleteMapping("/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteRole(@PathVariable UUID roleId) {
		ruoloService.findByIdAndDelete(roleId);
	}

}
