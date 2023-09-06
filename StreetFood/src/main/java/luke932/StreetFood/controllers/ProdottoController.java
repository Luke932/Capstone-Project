package luke932.StreetFood.controllers;

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

}
