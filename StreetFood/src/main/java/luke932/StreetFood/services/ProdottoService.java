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

import luke932.StreetFood.entities.Prodotto;
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.payloads.NewProdottoPayload;
import luke932.StreetFood.repositories.ProdottoRepository;

@Service
public class ProdottoService {

	private final ProdottoRepository prodottoR;

	@Autowired
	public ProdottoService(ProdottoRepository prodottoR) {
		this.prodottoR = prodottoR;
	}

	// -----------SALVATAGGIO PRODOTTI
	public Prodotto saveProdotto(Prodotto prodotto) {
		return prodottoR.save(prodotto);
	}

	// -----------GET PRODOTTI
	public List<Prodotto> findNoPage() {
		return prodottoR.findAll();
	}

	// -----------IMPAGINAZIONE GET CLIENTI
	public Page<Prodotto> find(int page, int size, String sort) {
		Pageable pag = PageRequest.of(page, size, Sort.by(sort));
		return prodottoR.findAll(pag);
	}

	// -----------------RICERCA PER NOME PRODOTTO
	public Prodotto findByNomeProdotto(String nomeProdotto) {
		return prodottoR.findByNomeProdotto(nomeProdotto);
	}

	// ------------RICERCA CLIENTE PER ID
	public Prodotto getProdottoByID(UUID id) {
		Optional<Prodotto> found = prodottoR.findById(id);
		return found.orElseThrow(() -> new NotFoundException("Prodotto non trovato con ID " + id));
	}

	// ------------MODIFICA CLIENTE PER ID
	public Prodotto updateCliente(UUID id, NewProdottoPayload body) {
		Prodotto found = this.getProdottoByID(id);
		found.setNomeProdotto(body.getNomeProdotto());
		found.setDescrizione(body.getDescrizione());
		found.setImmagine(body.getImmagine());
		found.setAltro(body.getAltro());

		return prodottoR.save(found);
	}

	// ------------CANCELLAZIONE CLIENTE PER ID
	public void deleteCliente(UUID id) {
		Prodotto found = getProdottoByID(id);
		prodottoR.delete(found);
	}
}
