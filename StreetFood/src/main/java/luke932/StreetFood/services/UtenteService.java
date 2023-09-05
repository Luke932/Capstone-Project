package luke932.StreetFood.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import luke932.StreetFood.entities.Ruolo;
import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.exceptions.BadRequestException;
import luke932.StreetFood.exceptions.ItemNotFoundException;
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.payloads.UtenteSavePayloadUser;
import luke932.StreetFood.payloads.UtenteUpdatePayload;
import luke932.StreetFood.repositories.UtenteRepository;

@Service
public class UtenteService {

	private final UtenteRepository utenteR;

	@Autowired
	public UtenteService(UtenteRepository utenteR) {
		this.utenteR = utenteR;
	}

	// -----------------CREATE UTENTE USER
	public Utente createUser(UtenteSavePayloadUser body) {
		utenteR.findByEmail(body.getEmail()).ifPresent(utente -> {
			throw new BadRequestException("L'email è stata già utilizzata");
		});
		Utente newUser = new Utente(body.getNome(), body.getCognome(), body.getUsername(), body.getEmail(),
				body.getPassword());
		return utenteR.save(newUser);
	}

	// -----------------RICERCA PER EMAIL
	public Utente findByEmail(String email) {
		return utenteR.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Utente con email" + email + "non trovato"));
	}

	// -----------------SALVA UTENTE
	public Utente save(Utente body) {
		if (body.getRuolo() == null) {
			Ruolo defaultRole = new Ruolo();
			defaultRole.setNome("USER");
			body.setRuolo(defaultRole);
		}
		return utenteR.save(body);
	}

	// -----------------RICERCA UTENTI PER PAGINA
	public Page<Utente> find(int page, int size, String sort) {
		Pageable pag = PageRequest.of(page, size, Sort.by(sort));
		return utenteR.findAll(pag);
	}

	// -----------------RICERCA UTENTI PER PAGINA
	public Utente findById(UUID id) throws ItemNotFoundException {
		return utenteR.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
	}

	// -----------------AGGIORNA UTENTE
	public Utente updateUtente(UUID id, UtenteUpdatePayload body) {
		Utente found = this.findById(id);
		found.setUsername(body.getUsername());
		found.setNome(body.getNome());
		found.setCognome(body.getCognome());
		found.setEmail(body.getEmail());
		found.setPassword(body.getPassword());
		found.setRuolo(body.getRuolo());

		return utenteR.save(found);
	}

	// -----------------ELIMINA UTENTE
	public void deleteUtente(UUID id) throws ItemNotFoundException {
		Utente found = this.findById(id);
		utenteR.delete(found);
	}
}
