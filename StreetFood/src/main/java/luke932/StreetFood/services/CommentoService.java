package luke932.StreetFood.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import luke932.StreetFood.entities.Commento;
import luke932.StreetFood.entities.Prodotto;
import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.repositories.CommentoRepository;
import luke932.StreetFood.repositories.ProdottoRepository;
import luke932.StreetFood.repositories.UtenteRepository;

@Service
public class CommentoService {

	private final CommentoRepository commentoR;
	private final UtenteRepository utenteR;
	private final ProdottoRepository prodottoR;

	@Autowired
	public CommentoService(CommentoRepository commentoR, UtenteRepository utenteR, ProdottoRepository prodottoR) {
		this.commentoR = commentoR;
		this.utenteR = utenteR;
		this.prodottoR = prodottoR;
	}

	// -------------CREAZIONE COMMENTO
	public Commento createCommento(UUID utenteId, UUID prodottoId, String testoCommento) {
		Utente utente = utenteR.findById(utenteId).orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
		Prodotto prodotto = prodottoR.findById(prodottoId)
				.orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato"));

		Commento commento = new Commento();
		commento.setUtente(utente);
		commento.setProdotto(prodotto);
		commento.setTestoCommento(testoCommento);
		commento.setDataCommento(LocalDate.now());

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
	public Commento updateCommentoById(UUID commentoId, String nuovoTestoCommento) {
		Commento commento = commentoR.findById(commentoId)
				.orElseThrow(() -> new EntityNotFoundException("Commento non trovato"));
		commento.setTestoCommento(nuovoTestoCommento);
		return commentoR.save(commento);
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
