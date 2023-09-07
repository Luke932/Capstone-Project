package luke932.StreetFood.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import luke932.StreetFood.entities.Prodotto;
import luke932.StreetFood.payloads.NewProdottoPayload;
import luke932.StreetFood.services.ProdottoService;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {

	private final ProdottoService prodottoSrv;

	@Autowired
	public ProdottoController(ProdottoService prodottoSrv) {
		this.prodottoSrv = prodottoSrv;
	}

	// ------------RICERCA PRODOTTO PER ID
	@GetMapping("/{id}")
	public Prodotto getProdottoById(@PathVariable UUID id) {
		return prodottoSrv.getProdottoByID(id);
	}

	// ------------CREAZIONE PRODOTTI
	@PostMapping
	// @PreAuthorize("hasAuthority('AMMINISTRATORE')")
	@ResponseStatus(HttpStatus.CREATED)
	public Prodotto createProdotto(@RequestBody Prodotto prodotto) {
		return prodottoSrv.saveProdotto(prodotto);
	}

	// ------------IMPAGINAZIONE PRODOTTI
	@GetMapping
	public Page<Prodotto> getProdotti(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return prodottoSrv.find(page, size, sortBy);
	}

	// ------------MODIFICA PRODOTTO PER ID
	@PutMapping("/{id}")
	// @PreAuthorize("hasAuthority('AMMINISTRATORE')")
	public Prodotto updateProdotto(@PathVariable UUID id, @RequestBody NewProdottoPayload nuovoProdotto) {
		return prodottoSrv.updateProdotto(id, nuovoProdotto);
	}

	// ------------CANCELLAZIONE PRODOTTO PER ID
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	// @PreAuthorize("hasAuthority('AMMINISTRATORE')")
	public void deleteProdotto(@PathVariable UUID id) {
		prodottoSrv.deleteProdotto(id);
	}

	// ------------RICERCA PER NOME PRODOTTO
	@GetMapping("/prodotto/{nomeProdotto}")
	public Prodotto getProdottoByNome(@PathVariable String nomeProdotto) {
		return prodottoSrv.findByNomeProdotto(nomeProdotto);
	}

	// ------------TROVA TUTTI I PRODOTTI DI UN CERTO LUOGO
	@GetMapping("/prodotto/luogo/{luogoId}")
	public List<Prodotto> getProdottiByLuogo(@PathVariable UUID luogoId) {
		return prodottoSrv.findByLuogoId(luogoId);
	}

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO LIKE DA UN CERTO UTENTE
	@GetMapping("/prodotto/likes/utente/{utenteId}")
	public List<Prodotto> getProdottiByLikesUtente(@PathVariable UUID utenteId) {
		return prodottoSrv.findByLikesUtenteId(utenteId);
	}

	// ------------TROVA TUTTI I PRODOTTI DI UN CERTO LUOGO CON ALMENO UN CERTO
	// NUMERO DI LIKE
	@GetMapping("prodotto/luogo/{luogoId}/min-likes/{minLikes}")
	public List<Prodotto> getProdottiByLuogoAndMinLikes(@PathVariable UUID luogoId, @PathVariable int minLikes) {
		return prodottoSrv.findByLuogoAndMinLikes(luogoId, minLikes);
	}

	// ------------TROVA TUTTI I PRODOTTI CON ALMENO UN CERTO NUMERO DI LIKE
	@GetMapping("/prodotto/min-likes/{minLikes}")
	public List<Prodotto> getProdottiWithMinLikes(@PathVariable int minLikes) {
		return prodottoSrv.findProdottiConAlmenoNLike(minLikes);
	}

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO LIKE DA UTENTI CON UN
	// CERTO RUOLO
	@GetMapping("prodotto/likes/ruolo/{ruoloNome}")
	public List<Prodotto> getProdottiWithLikesFromUtentiConRuolo(@PathVariable String ruoloNome) {
		return prodottoSrv.findProdottiConLikeDaUtentiConRuolo(ruoloNome);
	}

	// ================================================================================

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO COMMENTI DA UN CERTO
	// UTENTE
	@GetMapping("prodotto/commenti/utente/{utenteId}")
	public List<Prodotto> getProdottiByCommentiUtente(@PathVariable UUID utenteId) {
		return prodottoSrv.findByCommentiUtenteId(utenteId);
	}

	// ------------TROVA TUTTI I PRODOTTI DI UN CERTO LUOGO CON ALMENO UN CERTO
	// NUMERO DI COMMENTI
	@GetMapping("prodotto/luogo/{luogoId}/min-commenti/{minCommenti}")
	public List<Prodotto> getProdottiByLuogoAndMinCommenti(@PathVariable UUID luogoId, @PathVariable int minCommenti) {
		return prodottoSrv.findByLuogoAndMinCommenti(luogoId, minCommenti);
	}

	// ------------TROVA TUTTI I PRODOTTI CON ALMENO UN CERTO NUMERO DI COMMENTI
	@GetMapping("prodotto/min-commenti/{minCommenti}")
	public List<Prodotto> getProdottiWithMinCommenti(@PathVariable int minCommenti) {
		return prodottoSrv.findProdottiConAlmenoNCommento(minCommenti);
	}

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO COMMENTI DA UTENTI CON
	// UN CERTO RUOLO
	@GetMapping("prodotto/commenti/ruolo/{ruoloNome}")
	public List<Prodotto> getProdottiWithCommentiFromUtentiConRuolo(@PathVariable String ruoloNome) {
		return prodottoSrv.findProdottiConCommentoDaUtentiConRuolo(ruoloNome);
	}

}
