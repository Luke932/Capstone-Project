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

import luke932.StreetFood.entities.Luogo;
import luke932.StreetFood.payloads.NewLuogoPayload;
import luke932.StreetFood.services.LuogoService;

@RestController
@RequestMapping("/luoghi")
public class LuogoController {

	private final LuogoService luogoSrv;

	public LuogoController(LuogoService luogoSrv) {
		this.luogoSrv = luogoSrv;
	}

	// ------------RICERCA LUOGO PER ID
	@GetMapping("/{id}")
	public Luogo getLuogoById(@PathVariable UUID id) {
		return luogoSrv.getLuogoByID(id);
	}

	// ------------SALVATAGGIO LUOGHI
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Luogo createLuogo(@RequestBody Luogo luogo) {
		return luogoSrv.saveLuogo(luogo);
	}

	// ------------IMPAGINAZIONE LUOGHI
	@GetMapping
	public Page<Luogo> getLuogo(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return luogoSrv.find(page, size, sortBy);
	}

	// ------------MODIFICA LUOGHI
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Luogo updateLuogo(@PathVariable UUID id, @RequestBody NewLuogoPayload nuovoLuogo) {
		return luogoSrv.updateLuogo(id, nuovoLuogo);
	}

	// ------------CANCELLAZIONE LUOGHI
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteLuogo(@PathVariable UUID id) {
		luogoSrv.deleteLuogo(id);
	}

	// ------------RICERCA PER TITOLO LUOGO
	@GetMapping("/titolo/{titolo}")
	public Luogo getTitoloById(@PathVariable String titolo) {
		return luogoSrv.findByTitolo(titolo);
	}

	// ------------TROVA TUTTI I LUOGHI CHE CONTENGONO UNA CERTA DESCRIZIONE
	@GetMapping("/byDescrizione/{descrizione}")
	public List<Luogo> getLuoghiByDescrizione(@PathVariable String descrizione) {
		return luogoSrv.findLuoghiByDescrizione(descrizione);
	}

	// ------------TROVA TUTTI I LUOGHI DATO UN NOME PRODOTTO
	@GetMapping("/conProdotto/{nomeProdotto}")
	public List<Luogo> getLuoghiConProdotto(@PathVariable String nomeProdotto) {
		return luogoSrv.findLuoghiConProdotto(nomeProdotto);
	}

}
