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

import luke932.StreetFood.entities.Luogo;
import luke932.StreetFood.entities.Prodotto;
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.payloads.NewProdottoPayload;
import luke932.StreetFood.repositories.LuogoRepository;
import luke932.StreetFood.repositories.ProdottoRepository;

@Service
public class ProdottoService {

	private final ProdottoRepository prodottoR;
	private final LuogoRepository luogoR;

	@Autowired
	public ProdottoService(ProdottoRepository prodottoR, LuogoRepository luogoR) {
		this.prodottoR = prodottoR;
		this.luogoR = luogoR;
	}

	// -----------SALVATAGGIO PRODOTTI
	public Prodotto saveProdotto(Prodotto prodotto) {
		Luogo luogo = luogoR.findByTitolo(prodotto.getTitolo());
		Prodotto newProd = new Prodotto(prodotto.getNomeProdotto(), prodotto.getDescrizione(), prodotto.getImmagine(),
				prodotto.getAltro(), luogo);
		return prodottoR.save(newProd);
	}

	// -----------GET PRODOTTI
	public List<Prodotto> findNoPage() {
		return prodottoR.findAll();
	}

	// -----------IMPAGINAZIONE GET PRODOTTI
	public Page<Prodotto> find(int page, int size, String sort) {
		Pageable pag = PageRequest.of(page, size, Sort.by(sort));
		return prodottoR.findAll(pag);
	}

	// -----------------RICERCA PER NOME PRODOTTO
	public Prodotto findByNomeProdotto(String nomeProdotto) {
		return prodottoR.findByNomeProdotto(nomeProdotto);
	}

	// ------------RICERCA PRODOTTO PER ID
	public Prodotto getProdottoByID(UUID id) {
		Optional<Prodotto> found = prodottoR.findById(id);
		return found.orElseThrow(() -> new NotFoundException("Prodotto non trovato con ID " + id));
	}
// ------------MODIFICA PRODOTTO PER ID

	public Prodotto updateProdotto(UUID id, NewProdottoPayload body) {
		Prodotto found = this.getProdottoByID(id);
		found.setNomeProdotto(body.getNomeProdotto());
		found.setDescrizione(body.getDescrizione());
		found.setImmagine(body.getImmagine());
		found.setAltro(body.getAltro());

		return prodottoR.save(found);
	}

	// ------------CANCELLAZIONE PRODOTTO PER ID
	public void deleteProdotto(UUID id) {
		Prodotto found = getProdottoByID(id);
		prodottoR.delete(found);
	}

	// ------------TROVA TUTTI I PRODOTTI DI UN CERTO LUOGO
	public List<Prodotto> findByLuogoId(UUID luogoId) {
		return prodottoR.findByLuogoId(luogoId);
	}

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO LIKE DA UN CERTO UTENTE
	public List<Prodotto> findByLikesUtenteId(UUID utenteId) {
		return prodottoR.findByLikesUtenteId(utenteId);
	}

	// ------------TROVA TUTTI I PRODOTTI DI UN CERTO LUOGO CON ALMENO UN CERTO
	// NUMERO DI LIKE
	public List<Prodotto> findByLuogoAndMinLikes(UUID luogoId, int minLikes) {
		return prodottoR.findByLuogoAndMinLikes(luogoId, minLikes);
	}

	// ------------TROVA TUTTI I PRODOTTI CON UN CERTO NUMERO DI LIKE
	public List<Prodotto> findProdottiConAlmenoNLike(int minLikes) {
		return prodottoR.findProdottiConAlmenoNLike(minLikes);
	}

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO LIKE DA UTENTI CON UN
	// CERTO RUOLO
	public List<Prodotto> findProdottiConLikeDaUtentiConRuolo(String ruoloNome) {
		return prodottoR.findProdottiConLikeDaUtentiConRuolo(ruoloNome);
	}

	// ================================================================================

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO COMMENTI DA UN CERTO UTENTE
	public List<Prodotto> findByCommentiUtenteId(UUID utenteId) {
		return prodottoR.findByCommentiUtenteId(utenteId);
	}

	// ------------TROVA TUTTI I PRODOTTI DI UN CERTO LUOGO CON ALMENO UN CERTO
	// NUMERO DI COMMENTI
	public List<Prodotto> findByLuogoAndMinCommenti(UUID luogoId, int minLikes) {
		return prodottoR.findByLuogoAndMinLikes(luogoId, minLikes);
	}

	// ------------TROVA TUTTI I PRODOTTI CON UN CERTO NUMERO DI COMMENTI
	public List<Prodotto> findProdottiConAlmenoNCommento(int minLikes) {
		return prodottoR.findProdottiConAlmenoNCommento(minLikes);
	}

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO COMMENTI DA UTENTI CON UN
	// CERTO RUOLO
	public List<Prodotto> findProdottiConCommentoDaUtentiConRuolo(String ruoloNome) {
		return prodottoR.findProdottiConCommentoDaUtentiConRuolo(ruoloNome);
	}

}
