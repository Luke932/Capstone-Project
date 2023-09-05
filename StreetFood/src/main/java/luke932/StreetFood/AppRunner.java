package luke932.StreetFood;

import java.time.LocalDate;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import luke932.StreetFood.entities.Commento;
import luke932.StreetFood.entities.Like;
import luke932.StreetFood.entities.Prodotto;
import luke932.StreetFood.entities.Ruolo;
import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.payloads.RuoloSavePayload;
import luke932.StreetFood.services.CommentoService;
import luke932.StreetFood.services.LikeService;
import luke932.StreetFood.services.ProdottoService;
import luke932.StreetFood.services.RuoloService;
import luke932.StreetFood.services.UtenteService;

@Component
public class AppRunner implements CommandLineRunner {

	@Autowired
	RuoloService ruoloSrv;
	@Autowired
	UtenteService utenteSrv;
	@Autowired
	ProdottoService prodottoSrv;
	@Autowired
	CommentoService commentoSrv;
	@Autowired
	LikeService likeSrv;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(Locale.ITALIAN);

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

		// ----------------CREAZIONE PRODOTTI

		for (int i = 0; i < 10; i++) {
			Prodotto product = new Prodotto();
			product.setNomeProdotto(faker.food().dish());
			product.setDescrizione(faker.lorem().sentence());
			product.setImmagine(faker.internet().image());
			product.setAltro(faker.internet().avatar());

			// prodottoSrv.saveProdotto(product);
		}

		// ----------------CREAZIONE COMMENTO
		Prodotto prodottiDalDB = prodottoSrv.findByNomeProdotto("Pierogi");
		Utente utentidalDb = utenteSrv.findByNome("Luca");
		Commento commento = new Commento();
		commento.setTestoCommento("ciao");
		commento.setDataCommento(LocalDate.now());
		commento.setProdotto(prodottiDalDB);
		commento.setUtente(utentidalDb);

		// commentoSrv.saveCommento(commento);

		// ----------------CREAZIONE LIKE
		Like like = new Like();
		like.setDataLike(LocalDate.now());
		like.setProdotto(prodottiDalDB);
		like.setUtente(utentidalDb);

		// likeSrv.saveLike(like);
	}

}
