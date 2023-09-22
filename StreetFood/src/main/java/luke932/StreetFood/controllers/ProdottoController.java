package luke932.StreetFood.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import luke932.StreetFood.entities.Prodotto;
import luke932.StreetFood.exceptions.NotFoundException;
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
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Prodotto createProdotto(@RequestBody Prodotto prodotto) {
		return prodottoSrv.saveProdotto(prodotto);
	}

	// ------------IMPAGINAZIONE PRODOTTI
	@GetMapping
	public Page<Prodotto> getProdotti(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return prodottoSrv.find(page, size, sortBy);
	}

	// ------------MODIFICA PRODOTTO PER ID
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Prodotto updateProdotto(@PathVariable UUID id, @RequestBody NewProdottoPayload nuovoProdotto) {
		return prodottoSrv.updateProdotto(id, nuovoProdotto);
	}

	// ------------CANCELLAZIONE PRODOTTO PER ID
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteProdotto(@PathVariable UUID id) {
		prodottoSrv.deleteProdotto(id);
	}

	// ------------RICERCA PER NOME PRODOTTO
	@GetMapping("/prodotto/{nomeProdotto}")
	public Prodotto getProdottoByNome(@PathVariable String nomeProdotto) {
		return prodottoSrv.findByNomeProdotto(nomeProdotto);
	}

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO LIKE DA UN CERTO UTENTE
	@GetMapping("/prodotto/likes/utente/{utenteId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Prodotto> getProdottiByLikesUtente(@PathVariable UUID utenteId) {
		List<Prodotto> prodotti = prodottoSrv.findByLikesUtenteId(utenteId);
		if (prodotti.isEmpty()) {
			throw new NotFoundException("Nessun prodotto trovato per l'utente con ID " + utenteId);
		}
		return prodotti;
	}

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO LIKE DA UTENTI CON UN
	// CERTO RUOLO
	@GetMapping("prodotto/likes/ruolo/{ruoloNome}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Prodotto> getProdottiWithLikesFromUtentiConRuolo(@PathVariable String ruoloNome) {
		return prodottoSrv.findProdottiConLikeDaUtentiConRuolo(ruoloNome);
	}

	// ================================================================================

	// ------------TROVA TUTTI I PRODOTTI CON ALMENO UN CERTO NUMERO DI COMMENTI
	@GetMapping("prodotto/min-commenti/{minCommenti}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Prodotto> getProdottiWithMinCommenti(@PathVariable int minCommenti) {
		return prodottoSrv.findProdottiConAlmenoNCommento(minCommenti);
	}

	// ------------TROVA TUTTI I PRODOTTI CHE HANNO RICEVUTO COMMENTI DA UTENTI CON
	// UN CERTO RUOLO
	@GetMapping("prodotto/commenti/ruolo/{ruoloNome}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Prodotto> getProdottiWithCommentiFromUtentiConRuolo(@PathVariable String ruoloNome) {
		return prodottoSrv.findProdottiConCommentoDaUtentiConRuolo(ruoloNome);
	}

	// ================================================================================

	// ------------TROVA TUTTI I PRODOTTI DI UN CERTO LUOGO CON UN MINIMO DI LIKE
	@GetMapping("/luogoMinLikes")
	public List<Prodotto> findProdottiByLuogoAndMinLikes(@RequestParam UUID luogoId, @RequestParam int minLikes) {
		return prodottoSrv.findProdottiByLuogoAndMinLikes(luogoId, minLikes);
	}

	// ------------TROVA TUTTI I PRODOTTI DI UN CERTO LUOGO
	@GetMapping("/luoghi/{titoloLuogo}")
	public List<Prodotto> getProdottiByTitoloLuogo(@PathVariable String titoloLuogo) {
		return prodottoSrv.findProdottiByTitoloLuogo(titoloLuogo);
	}

	// ------------TROVA TUTTI I PRODOTTI TRAMITE IL NOME DI UN PRODOTTO E UNA LISTA
	@GetMapping("/nomeLuoghi")
	public List<Prodotto> findProdottiByNomeAndLuoghi(@RequestParam String nome, @RequestParam List<UUID> luoghiIds) {
		return prodottoSrv.findProdottiByNomeAndLuoghi(nome, luoghiIds);
	}

	// ------------------------TROVA TUTTI I PRODOTTI TRAMITE UN NUMERO DI LIKE
	@GetMapping("/numLikes")
	public List<Prodotto> findProdottiByNumLikes(@RequestParam int numLikes) {
		return prodottoSrv.findProdottiByNumLikes(numLikes);
	}

	// ------------------------TROVA TUTTI I PRODOTTI TRAMITE UNA LISTA DI LUOGHI ED
	// UN NUMERO DI LIKE
	@GetMapping("/luoghiNumLikes")
	public List<Prodotto> findProdottiByLuoghiAndNumLikes(@RequestParam List<UUID> luoghiIds,
			@RequestParam int numLikes) {
		return prodottoSrv.findProdottiByLuoghiAndNumLikes(luoghiIds, numLikes);
	}

}
