package luke932.StreetFood.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import luke932.StreetFood.entities.Commento;
import luke932.StreetFood.entities.Ruolo;
import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.exceptions.BadRequestException;
import luke932.StreetFood.exceptions.ItemNotFoundException;
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.payloads.UtenteSavePayloadUser;
import luke932.StreetFood.payloads.UtenteUpdatePayload;
import luke932.StreetFood.repositories.CommentoRepository;
import luke932.StreetFood.repositories.RuoloRepository;
import luke932.StreetFood.repositories.UtenteRepository;

@Service
public class UtenteService {

	private final UtenteRepository utenteR;
	private final RuoloRepository ruoloR;
	private final CommentoRepository commentoR;

	@Autowired
	public UtenteService(UtenteRepository utenteR, RuoloRepository ruoloR, CommentoRepository commentoR) {
		this.utenteR = utenteR;
		this.ruoloR = ruoloR;
		this.commentoR = commentoR;
	}

	// -----------------CREATE UTENTE USER
	public Utente createUser(UtenteSavePayloadUser body) {
		utenteR.findByEmail(body.getEmail()).ifPresent(utente -> {
			throw new BadRequestException("L'email è stata già utilizzata");
		});

		Ruolo userRole = ruoloR.findByNome("USER");
		System.out.println(userRole);
		if (userRole == null) {
			throw new NotFoundException("Ruolo 'USER' non trovato nel database");
		}

		Utente newUser = new Utente(body.getNome(), body.getCognome(), body.getUsername(), body.getEmail(),
				body.getPassword(), body.getFoto());

		newUser.setRuolo(userRole);

		return utenteR.save(newUser);
	}

	// -----------------CREATE UTENTE ADMIN
	public Utente createAdminUser(UtenteSavePayloadUser body) {

		utenteR.findByEmail(body.getEmail()).ifPresent(utente -> {
			throw new BadRequestException("L'email è stata già utilizzata");
		});

		Ruolo adminRole = ruoloR.findByNome("ADMIN");
		if (adminRole == null) {
			throw new NotFoundException("Ruolo 'ADMIN' non trovato nel database");
		}

		Utente newAdminUser = new Utente(body.getNome(), body.getCognome(), body.getUsername(), body.getEmail(),
				body.getPassword(), body.getFoto());

		newAdminUser.setRuolo(adminRole);

		return utenteR.save(newAdminUser);
	}

	// -----------------RICERCA PER EMAIL
	public Utente findByEmail(String email) {
		return utenteR.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Utente con email" + email + "non trovato"));
	}

	// -----------------SALVA UTENTE
	public Utente save(Utente body) {
		if (body.getRuolo() == null || body.getRuolo().getNome() == null) {
			Ruolo userRole = ruoloR.findByNome("USER");

			if (userRole == null) {
				throw new NotFoundException("Ruolo 'USER' non trovato nel database");
			}
			body.setRuolo(userRole);
		}

		return utenteR.save(body);
	}

	// -----------------RICERCA PER RUOLO
	public Utente findByNome(String nome) {
		return utenteR.findByNome(nome);
	}

	// -----------------RICERCA PER USERNAME
	public Utente findByUsername(String username) {
		return utenteR.findByUsername(username);
	}

	// -----------------GET UTENTI
	public List<Utente> findNoPage() {
		return utenteR.findAll();
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

		String nomeRuolo = body.getNomeRuolo();
		System.out.println("Nome Ruolo nel payload: " + nomeRuolo);

		Ruolo ruolo = ruoloR.findByNome(body.getNomeRuolo());

		if (ruolo != null) {
			found.setUsername(body.getUsername());
			found.setNome(body.getNome());
			found.setCognome(body.getCognome());
			found.setEmail(body.getEmail());
			found.setPassword(body.getPassword());

			found.setRuolo(ruolo);

			return utenteR.save(found);
		} else {
			throw new RuntimeException("Ruolo non trovato: " + body.getNomeRuolo());
		}
	}

	// -----------------ELIMINA UTENTE
	public void deleteUtente(UUID id) throws ItemNotFoundException {
		Utente found = this.findById(id);
		utenteR.delete(found);
	}

	// -----------------RICERCA TUTTI GLI UTENTI CHE HANNO COMMENTATO UN DETERMINATO
	// PRODOTTO
	public List<Utente> findUtentiByProdottoCommentato(UUID prodottoId) {
		return utenteR.findUtentiByProdottoCommentato(prodottoId);
	}

	// -----------------RICERCA TUTTI GLI UTENTI CHE HANNO COMMENTATO UN DETERMINATO
	// PRODOTTO E CHE HANNO UN CERTO RUOLO
	public List<Utente> findUtentiByProdottoCommentatoAndRuolo(UUID prodottoId, String ruoloNome) {
		return utenteR.findUtentiByProdottoCommentatoAndRuolo(prodottoId, ruoloNome);
	}

	// -----------------RICERCA TUTTI GLI UTENTI CHE HANNO COMMENTATO ALMENO UN
	// PRODOTTO
	public List<Utente> findUtentiConCommenti() {
		return utenteR.findUtentiConCommenti();
	}

	// -----------------RICERCA TUTTI GLI UTENTI CHE HANNO MESSO LIKE AD ALMENO UN
	// PRODOTTO
	public List<Utente> findUtentiConLike() {
		return utenteR.findUtentiConLike();
	}

	// -----------------RECUPERO LA FOTO DELL'UTENTE TRAMITE L'ID COMMENTO
	public byte[] getFotoUtenteByCommentoId(UUID commentoId) {
		Optional<Commento> commentoOptional = commentoR.findById(commentoId);
		if (commentoOptional.isPresent()) {
			Commento commento = commentoOptional.get();
			return commento.getUtente().getFoto();
		} else {
			throw new NotFoundException("Commento non trovato con ID: " + commentoId);
		}
	}

}
