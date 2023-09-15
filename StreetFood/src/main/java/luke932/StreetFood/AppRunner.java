package luke932.StreetFood;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import luke932.StreetFood.entities.Commento;
import luke932.StreetFood.entities.Like;
import luke932.StreetFood.entities.Luogo;
import luke932.StreetFood.entities.Prodotto;
import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.payloads.RuoloSavePayload;
import luke932.StreetFood.services.CommentoService;
import luke932.StreetFood.services.LikeService;
import luke932.StreetFood.services.LuogoService;
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
	LuogoService luogoSrv;
	@Autowired
	CommentoService commentoSrv;
	@Autowired
	LikeService likeSrv;
	@Autowired
	PasswordEncoder bcrypt;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(Locale.ITALIAN);

		// ----------------CREAZIONE RUOLI
		RuoloSavePayload ruolo1 = new RuoloSavePayload(false, true, "ADMIN");
		RuoloSavePayload ruolo2 = new RuoloSavePayload(true, false, "USER");
//		ruoloSrv.save(ruolo1);
//		ruoloSrv.save(ruolo2);

		// ----------------CREAZIONE LUOGHI
		for (int i = 0; i < 10; i++) {
			Luogo luogo = new Luogo();
			luogo.setTitolo(faker.address().city());
			luogo.setImmagine(faker.internet().image());
			luogo.setDescrizione(faker.lorem().paragraph());
			// luogoSrv.saveLuogo(luogo);
		}

		// ----------------CREAZIONE PRODOTTI
		List<Luogo> luoghiDalDb = new ArrayList<>();
		luoghiDalDb = luogoSrv.findNoPage();
		for (int i = 0; i < 10; i++) {
			Prodotto product = new Prodotto();
			product.setNomeProdotto(faker.food().dish());
			product.setDescrizione(faker.lorem().sentence());
			product.setImmagine(faker.internet().image());
			product.setAltro(faker.internet().avatar());
			product.setLuogo(luoghiDalDb.get(faker.number().numberBetween(0, luoghiDalDb.size() - 1)));
			// prodottoSrv.saveProdotto(product);
		}

		// ----------------CREAZIONE COMMENTO
		Prodotto prodottiDalDb = prodottoSrv.findByNomeProdotto("Tacos");
		Utente utentidalDb = utenteSrv.findByNome("Luca");
		Commento commento = new Commento();
		commento.setTestoCommento("ciao");
		commento.setDataCommento(LocalDate.now());
		commento.setProdotto(prodottiDalDb);
		commento.setUtente(utentidalDb);

		// commentoSrv.saveCommento(commento);

		// ----------------CREAZIONE LIKE
		Like like = new Like();
		like.setDataLike(LocalDate.now());
		like.setProdotto(prodottiDalDb);
		like.setUtente(utentidalDb);

		// likeSrv.saveLike(like);
	}

}
