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
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.repositories.CommentoRepository;

@Service
public class CommentoService {

	private final CommentoRepository commentoR;

	@Autowired
	public CommentoService(CommentoRepository commentoR) {
		this.commentoR = commentoR;
	}

	// -------------CREAZIONE COMMENTO
	public Commento saveCommento(Commento commento) {
		return commentoR.save(commento);
	}

	// -----------IMPAGINAZIONE GET COMMENTI
	public Page<Commento> find(int page, int size, String sort) {
		Pageable pag = PageRequest.of(page, size, Sort.by(sort));
		return commentoR.findAll(pag);
	}

	// ------------RICERCA COMMENTO PER ID
	public Commento getCommentoByID(UUID id) {
		Optional<Commento> found = commentoR.findById(id);
		return found.orElseThrow(() -> new NotFoundException("Commento non trovato con ID " + id));
	}

	// ------------MODIFICA COMMENTO PER ID
	public Commento updateCommento(UUID id, Commento body) {
		Commento found = this.getCommentoByID(id);
		found.setTestoCommento(body.getTestoCommento());
		found.setProdotto(body.getProdotto());
		found.setUtente(body.getUtente());
		found.setDataCommento(body.getDataCommento());

		return commentoR.save(found);
	}

	// ------------CANCELLAZIONE COMMENTO PER ID
	public void deleteCommento(UUID id) {
		Commento found = getCommentoByID(id);
		commentoR.delete(found);
	}

	// ------------RESTITUISCE UNA LISTA DI COMMENTI ASSOCIATI A QUESTO PRODOTTO
	public List<Commento> findByProdottoId(UUID prodottoId) {
		return commentoR.findByProdottoId(prodottoId);
	}

	// ------------RESTITUISCE UNA LISTA DI COMMENTI ASSOCIATI A QUESTO UTENTE
	public List<Commento> findByUtenteId(UUID utenteId) {
		return commentoR.findByUtenteId(utenteId);
	}

	// ------------RESTITUISCE UN NUMERO DI COMMENTI ASSOCIATI A QUESTO PRODOTTO
	public Long countCommentiByProdottoId(UUID prodottoId) {
		return commentoR.countCommentiByProdottoId(prodottoId);
	}

	// ------------RESTITUISCE UN NUMERO DI COMMENTI ASSOCIATI A QUESTO UTENTE
	public Long countCommentiByUtenteId(UUID utenteId) {
		return commentoR.countCommentiByUtenteId(utenteId);
	}

}
