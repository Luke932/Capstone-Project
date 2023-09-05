package luke932.StreetFood.services;

import java.util.List;

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

	// SALVA RUOLO
	public Ruolo save(RuoloSavePayload body) {
		Ruolo nuovoRuolo = new Ruolo(body.getNome(), body.getRuoloUser(), body.getRuoloAdmin());
		return ruoloRepository.save(nuovoRuolo);
	}

	// CERCA RUOLI
	public List<Ruolo> findAll() {
		return ruoloRepository.findAll();
	}

	// CERCA RUOLO PER ID
	public Ruolo findById(String sigla) throws RuoloNotFoundException {
		return ruoloRepository.findById(sigla).orElseThrow(() -> new RuoloNotFoundException(sigla));
	}

	// AGGIORNA RUOLO
	public Ruolo findByIdAndUpdate(String nome, Ruolo body) throws ItemNotFoundException {
		Ruolo found = this.findById(nome);

		found.setRuoloUser(body.getRuoloUser());
		found.setRuoloAdmin(body.getRuoloAdmin());

		return ruoloRepository.save(found);

	}

	// ELIMINA RUOLO
	public void findByIdAndDelete(String id) throws ItemNotFoundException {
		Ruolo found = this.findById(id);
		ruoloRepository.delete(found);
	}

}
