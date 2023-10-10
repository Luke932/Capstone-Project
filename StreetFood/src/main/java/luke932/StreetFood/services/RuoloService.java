package luke932.StreetFood.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import luke932.StreetFood.entities.Ruolo;
import luke932.StreetFood.exceptions.ItemNotFoundException;
import luke932.StreetFood.exceptions.RuoloNotFoundException;
import luke932.StreetFood.payloads.RuoloSavePayload;
import luke932.StreetFood.repositories.RuoloRepository;

@Service
public class RuoloService {

	private final RuoloRepository ruoloRepository;

	@Autowired
	public RuoloService(RuoloRepository ruoloRepository) {
		this.ruoloRepository = ruoloRepository;
	}

	// --------------- SALVA RUOLO
	public Ruolo save(RuoloSavePayload body) {
		Ruolo nuovoRuolo = new Ruolo(body.getNome(), body.getRuoloUser(), body.getRuoloAdmin());
		return ruoloRepository.save(nuovoRuolo);
	}

	// --------------- CERCA RUOLI
	public List<Ruolo> findAll() {
		return ruoloRepository.findAll();
	}

	// --------------- CERCA RUOLO PER ID
	public Ruolo findById(UUID id) throws RuoloNotFoundException {
		return ruoloRepository.findById(id).orElseThrow(() -> new RuoloNotFoundException(id));
	}

	// --------------- CERCA RUOLO PER NOME
	public Ruolo findByNome(String nome) throws RuoloNotFoundException {
		return ruoloRepository.findByNome(nome);
	}

	// --------------- AGGIORNA RUOLO
	public Ruolo findByIdAndUpdate(UUID id, Ruolo body) throws ItemNotFoundException {
		Ruolo found = this.findById(id);

		found.setNome(body.getNome());
		found.setRuoloUser(body.getRuoloUser());
		found.setRuoloAdmin(body.getRuoloAdmin());

		return ruoloRepository.save(found);

	}

	// --------------- ELIMINA RUOLO
	public void findByIdAndDelete(UUID id) throws ItemNotFoundException {
		Ruolo found = this.findById(id);
		ruoloRepository.delete(found);
	}

}
