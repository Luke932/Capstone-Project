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

import luke932.StreetFood.entities.Like;
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.repositories.LikeRepository;

@Service
public class LikeService {

	private final LikeRepository likeR;

	@Autowired
	public LikeService(LikeRepository LikeR) {
		this.likeR = LikeR;
	}

	// -------------CREAZIONE LIKE
	public Like saveLike(Like like) {
		return likeR.save(like);
	}

	// -----------IMPAGINAZIONE GET LIKE
	public Page<Like> find(int page, int size, String sort) {
		Pageable pag = PageRequest.of(page, size, Sort.by(sort));
		return likeR.findAll(pag);
	}

	// ------------RICERCA LIKE PER ID
	public Like getLikeoByID(UUID id) {
		Optional<Like> found = likeR.findById(id);
		return found.orElseThrow(() -> new NotFoundException("Commento non trovato con ID " + id));
	}

	// ------------MODIFICA CLIENTE PER ID
	public Like updateLike(UUID id, Like body) {
		Like found = this.getLikeoByID(id);
		found.setProdotto(body.getProdotto());
		found.setUtente(body.getUtente());
		found.setDataLike(body.getDataLike());

		return likeR.save(found);
	}

	// ------------CANCELLAZIONE CLIENTE PER ID
	public void deleteLike(UUID id) {
		Like found = getLikeoByID(id);
		likeR.delete(found);
	}

	// ------------RESTITUISCE UNA LISTA DI LIKE ASSOCIATI A QUESTO PRODOTTO
	public List<Like> findByProdottoId(UUID prodottoId) {
		return likeR.findByProdottoId(prodottoId);
	}

	// ------------RESTITUISCE UNA LISTA DI LIKE ASSOCIATI A QUESTO UTENTE
	public List<Like> findByUtenteId(UUID utenteId) {
		return likeR.findByUtenteId(utenteId);
	}

	// ------------RESTITUISCE UNA NUMERO DI LIKE ASSOCIATI A QUESTO PRODOTTO
	public Long countLikesByProdottoId(UUID prodottoId) {
		return likeR.countLikesByProdottoId(prodottoId);
	}

	// ------------RESTITUISCE UNA NUMERO DI LIKE ASSOCIATI A QUESTO PRODOTTO
	public Long countLikesByUtenteId(UUID utenteId) {
		return likeR.countLikesByUtenteId(utenteId);
	}

}
