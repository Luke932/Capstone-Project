package luke932.StreetFood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import luke932.StreetFood.entities.Ruolo;
import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.payloads.RuoloSavePayload;
import luke932.StreetFood.services.RuoloService;
import luke932.StreetFood.services.UtenteService;

@Component
public class AppRunner implements CommandLineRunner {

	@Autowired
	RuoloService ruoloSrv;
	@Autowired
	UtenteService utenteSrv;

	@Override
	public void run(String... args) throws Exception {

		// ----------------CREAZIONE RUOLI
		RuoloSavePayload ruolo1 = new RuoloSavePayload(false, true, "ADMIN");
		RuoloSavePayload ruolo2 = new RuoloSavePayload(true, false, "USER");
//		ruoloSrv.save(ruolo1);
//		ruoloSrv.save(ruolo2);

		// ----------------CREAZIONE ADMIN
		Ruolo ruoloAdmin = new Ruolo();
		ruoloAdmin.setNome("ADMIN");

		Utente utenteAdmin = new Utente("Luke932", "Luca", "Giacalone", "luca_g@gmail.com", "1234");
		utenteAdmin.setRuolo(ruoloAdmin);
		// utenteSrv.save(utenteAdmin);
	}

}
