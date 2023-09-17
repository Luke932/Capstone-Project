package luke932.StreetFood.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import luke932.StreetFood.entities.Like;
import luke932.StreetFood.entities.Prodotto;
import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.repositories.ProdottoRepository;
import luke932.StreetFood.repositories.UtenteRepository;
import luke932.StreetFood.services.LikeService;

@RestController
@RequestMapping("/like")
public class LikeController {

	private final LikeService likeSrv;
	private final UtenteRepository utenteRepository;
	private final ProdottoRepository prodottoRepository;

	public LikeController(LikeService likeSrv, UtenteRepository utenteRepository,
			ProdottoRepository prodottoRepository) {
		this.likeSrv = likeSrv;
		this.utenteRepository = utenteRepository;
		this.prodottoRepository = prodottoRepository;
	}

	// ------------RICERCA LIKE PER ID
	@GetMapping("/{id}")
	public Like getLikeById(@PathVariable UUID id) {
		return likeSrv.getLikeoByID(id);
	}

	// ------------SALVATAGGIO LIKE
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createAndDeleteLike(@RequestBody Map<String, String> likeData) {
		UUID utenteId = UUID.fromString(likeData.get("utenteId"));
		UUID prodottoId = UUID.fromString(likeData.get("prodottoId"));

		Like existingLike = likeSrv.getLikeByUserAndProduct(utenteId, prodottoId);

		if (existingLike != null) {
			likeSrv.deleteLike(existingLike.getId());
			return ResponseEntity.ok("Like rimosso");
		} else {
			Utente utente = new Utente();
			utente.setId(utenteId);

			Prodotto prodotto = new Prodotto();
			prodotto.setId(prodottoId);

			Like like = new Like();
			like.setUtente(utente);
			like.setProdotto(prodotto);
			like.setDataLike(LocalDate.now());

			Like savedLike = likeSrv.saveLike(like);
			return ResponseEntity.ok(savedLike);
		}
	}

	// ------------SALVATAGGIO LIKE
	@PostMapping("/create")
	public String createLike(@RequestParam String utenteId, @RequestParam String prodottoId) {
		UUID utenteUUID = UUID.fromString(utenteId);
		UUID prodottoUUID = UUID.fromString(prodottoId);

		Utente utente = utenteRepository.findById(utenteUUID).orElse(null);
		Prodotto prodotto = prodottoRepository.findById(prodottoUUID).orElse(null);

		if (utente != null && prodotto != null) {
			likeSrv.createLike(utente, prodotto);
			return "Like creato con successo.";
		} else {
			return "Utente o prodotto non trovato.";
		}
	}

	// ------------IMPAGINAZIONE LIKE
	@GetMapping
	public Page<Like> getLuogo(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return likeSrv.find(page, size, sortBy);
	}

	// ------------MODIFICA LIKE
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Like updateLuogo(@PathVariable UUID id, @RequestBody Like like) {
		return likeSrv.updateLike(id, like);
	}

	// ------------CANCELLAZIONE LIKE
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLike(@PathVariable UUID id) {
		likeSrv.deleteLike(id);
	}

	// ------------CANCELLAZIONE LIKE
	@DeleteMapping("/delete")
	public String deleteLikes(@RequestParam UUID likeId) {
		likeSrv.deleteLike(likeId);
		return "Like eliminato con successo.";
	}

	// ------------RESTITUISCE UNA LISTA DI LIKE ASSOCIATI A QUESTO PRODOTTO
	@GetMapping("/byProdotto/{prodottoId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Like> getLikesByProdottoId(@PathVariable UUID prodottoId) {
		List<Like> likes = likeSrv.findByProdottoId(prodottoId);
		if (likes.isEmpty()) {
			throw new NotFoundException("Nessun like trovato per il prodotto con ID " + prodottoId);
		}
		return likes;
	}

	// ------------RESTITUISCE UNA LISTA DI LIKE ASSOCIATI A QUESTO UTENTE
	@GetMapping("/byUtente/{utenteId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Like> getLikesByUtenteId(@PathVariable UUID utenteId) {
		List<Like> likes = likeSrv.findByUtenteId(utenteId);
		if (likes.isEmpty()) {
			throw new NotFoundException("Nessun like trovato per l'utente con ID " + utenteId);
		}
		return likes;
	}

	// ------------RESTITUISCE UN NUMERO DI LIKE ASSOCIATI A QUESTO PRODOTTO
	@GetMapping("/countByProdotto/{prodottoId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Long countLikesByProdottoId(@PathVariable UUID prodottoId) {
		Long likeCount = likeSrv.countLikesByProdottoId(prodottoId);
		if (likeCount == 0) {
			throw new NotFoundException("Nessun like trovato per il prodotto con ID " + prodottoId);
		}
		return likeCount;
	}

	// ------------RESTITUISCE UN NUMERO DI LIKE ASSOCIATI A QUESTO UTENTE
	@GetMapping("/countByUtente/{utenteId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Long countLikesByUtenteId(@PathVariable UUID utenteId) {
		Long likeCount = likeSrv.countLikesByUtenteId(utenteId);
		if (likeCount == 0) {
			throw new NotFoundException("Nessun like trovato per l'utente con ID " + utenteId);
		}
		return likeCount;
	}

	// -------------METODO PER TROVARE UN LIKE SPECIFICO DI UN PRODOTTO DA PARTE DI
	// UTENTE
	@GetMapping("/{utenteId}/{prodottoId}")
	public ResponseEntity<Like> getLikeByUserAndProduct(@PathVariable UUID utenteId, @PathVariable UUID prodottoId) {
		Like like = likeSrv.getLikeByUserAndProduct(utenteId, prodottoId);
		return ResponseEntity.ok(like);
	}

}
