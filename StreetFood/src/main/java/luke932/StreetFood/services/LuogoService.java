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

import jakarta.transaction.Transactional;
import luke932.StreetFood.entities.Luogo;
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.payloads.NewLuogoPayload;
import luke932.StreetFood.repositories.LuogoRepository;

@Service
public class LuogoService {

	private final LuogoRepository luogoR;

	@Autowired
	public LuogoService(LuogoRepository luogoR) {
		this.luogoR = luogoR;
	}

	// -----------SALVATAGGIO LUOGHI
	@Transactional
	public Luogo saveLuogo(Luogo luogo) {
		return luogoR.save(luogo);
	}

	// -----------GET LUOGHI
	public List<Luogo> findNoPage() {
		return luogoR.findAll();
	}

	// -----------IMPAGINAZIONE GET LUOGHI
	public Page<Luogo> find(int page, int size, String sort) {
		Pageable pag = PageRequest.of(page, size, Sort.by(sort));
		return luogoR.findAll(pag);
	}

	// -----------------RICERCA PER TITOLO PRODOTTO
	public Luogo findByTitolo(String titolo) {
		Luogo luogo = luogoR.findByTitolo(titolo);

		if (luogo == null) {
			return null;
		} else {
			return luogo;
		}
	}

	// ------------RICERCA LUOGO PER ID
	public Luogo getLuogoByID(UUID id) {
		Optional<Luogo> found = luogoR.findById(id);
		return found.orElseThrow(() -> new NotFoundException("Luogo non trovato con ID " + id));
	}

	// ------------MODIFICA LUOGO PER ID
	public Luogo updateLuogo(UUID id, NewLuogoPayload body) {
		Luogo found = this.getLuogoByID(id);
		found.setTitolo(body.getTitolo());
		found.setImmagine(body.getImmagine());
		found.setDescrizione(body.getDescrizione());

		return luogoR.save(found);
	}

	// ------------CANCELLAZIONE LUOGO PER ID
	public void deleteLuogo(UUID id) {
		Luogo found = getLuogoByID(id);
		luogoR.delete(found);
	}

	// ------------TROVA TUTTI I LUOGHI CHE CONTENGONO UNA CERTA DESCRIZIONE
	public List<Luogo> findLuoghiByDescrizione(String descrizione) {
		List<Luogo> luoghi = luogoR.findByDescrizioneContaining(descrizione);

		if (luoghi.isEmpty()) {
			return null;
		} else {
			return luoghi;
		}
	}

	// ------------TROVA TUTTI I LUOGHI DATO UN NOME PRODOTTO
	public List<Luogo> findLuoghiConProdotto(String nomeProdotto) {
		List<Luogo> luoghi = luogoR.findLuoghiConProdotto(nomeProdotto);

		if (luoghi.isEmpty()) {
			return null;
		} else {
			return luoghi;
		}
	}

}
