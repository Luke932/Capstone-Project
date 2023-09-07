package luke932.StreetFood.controllers;

import java.util.UUID;

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

import luke932.StreetFood.entities.Like;
import luke932.StreetFood.services.LikeService;

@RestController
@RequestMapping("/like")
public class LikeController {

	private final LikeService likeSrv;

	public LikeController(LikeService likeSrv) {
		this.likeSrv = likeSrv;
	}

	// ------------RICERCA LIKE PER ID
	@GetMapping("/{id}")
	public Like getLikeById(@PathVariable UUID id) {
		return likeSrv.getLikeoByID(id);
	}

	// ------------SALVATAGGIO LIKE
	@PostMapping
	// @PreAuthorize("hasAuthority('AMMINISTRATORE')")
	@ResponseStatus(HttpStatus.CREATED)
	public Like createCommento(@RequestBody Like like) {
		return likeSrv.saveLike(like);
	}

	// ------------IMPAGINAZIONE LIKE
	@GetMapping
	public Page<Like> getLuogo(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return likeSrv.find(page, size, sortBy);
	}

	// ------------MODIFICA LIKE
	@PutMapping("/{id}")
	// @PreAuthorize("hasAuthority('AMMINISTRATORE')")
	public Like updateLuogo(@PathVariable UUID id, @RequestBody Like like) {
		return likeSrv.updateLike(id, like);
	}

	// ------------CANCELLAZIONE LIKE
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	// @PreAuthorize("hasAuthority('AMMINISTRATORE')")
	public void deleteLike(@PathVariable UUID id) {
		likeSrv.deleteLike(id);
	}

}
