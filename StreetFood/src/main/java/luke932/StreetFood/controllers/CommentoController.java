package luke932.StreetFood.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import luke932.StreetFood.entities.Commento;
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.services.CommentoService;

@RestController
@RequestMapping("/commenti")
public class CommentoController {

	private final CommentoService commentoSrv;

	public CommentoController(CommentoService commentoSrv) {
		this.commentoSrv = commentoSrv;
	}

	// ------------RICERCA COMMENTO PER ID
	@GetMapping("/{id}")
	public Commento getCommentoById(@PathVariable UUID id) {
		return commentoSrv.getCommentoByID(id);
	}

	// ------------SALVATAGGIO COMMENTI
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Commento createCommento(@RequestBody Commento commento) {
		return commentoSrv.saveCommento(commento);
	}

	// ------------IMPAGINAZIONE COMMENTI
	@GetMapping
	public Page<Commento> getLuogo(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return commentoSrv.find(page, size, sortBy);
	}

	// ------------MODIFICA COMMENTI
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Commento updateLuogo(@PathVariable UUID id, @RequestBody Commento commento) {
		return commentoSrv.updateCommento(id, commento);
	}

	// ------------CANCELLAZIONE COMMENTI
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteCommento(@PathVariable UUID id) {
		commentoSrv.deleteCommento(id);
	}

	// ------------RESTITUISCE UNA LISTA DI COMMENTI ASSOCIATI A QUESTO PRODOTTO
	@GetMapping("/byProdotto/{prodottoId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Commento> getCommentiByProdottoId(@PathVariable UUID prodottoId) {
		List<Commento> commenti = commentoSrv.findByProdottoId(prodottoId);
		if (commenti.isEmpty()) {
			throw new NotFoundException("Nessun commento trovato per il prodotto con ID " + prodottoId);
		}
		return commenti;
	}

	// ------------RESTITUISCE UNA LISTA DI COMMENTI ASSOCIATI A QUESTO UTENTE
	@GetMapping("/byUtente/{utenteId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Commento> getCommentiByUtenteId(@PathVariable UUID utenteId) {
		List<Commento> commenti = commentoSrv.findByUtenteId(utenteId);
		if (commenti.isEmpty()) {
			throw new NotFoundException("Nessun commento trovato per l'utente con ID " + utenteId);
		}
		return commenti;
	}

	// ------------RESTITUISCE UN NUMERO DI COMMENTI ASSOCIATI A QUESTO PRODOTTO
	@GetMapping("/countByProdotto/{prodottoId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Long countCommentiByProdottoId(@PathVariable UUID prodottoId) {
		Long commentCount = commentoSrv.countCommentiByProdottoId(prodottoId);
		if (commentCount == 0) {
			throw new NotFoundException("Nessun commento trovato per il prodotto con ID " + prodottoId);
		}
		return commentCount;
	}

	// ------------RESTITUISCE UN NUMERO DI COMMENTI ASSOCIATI A QUESTO UTENTE
	@GetMapping("/countByUtente/{utenteId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Long countCommentiByUtenteId(@PathVariable UUID utenteId) {
		Long commentCount = commentoSrv.countCommentiByUtenteId(utenteId);
		if (commentCount == 0) {
			throw new NotFoundException("Nessun commento trovato per l'utente con ID " + utenteId);
		}
		return commentCount;
	}
}
