package luke932.StreetFood;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import jakarta.transaction.Transactional;
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
	@Transactional
	public void run(String... args) throws Exception {
		Faker faker = new Faker(Locale.ITALIAN);

		// ----------------CREAZIONE RUOLI
		RuoloSavePayload ruolo1 = new RuoloSavePayload(false, true, "ADMIN");
		RuoloSavePayload ruolo2 = new RuoloSavePayload(true, false, "USER");
//		ruoloSrv.save(ruolo1);
//		ruoloSrv.save(ruolo2);

		// ----------------ASSOCIAZIONE TRA LUOGHI E PRODOTTI
//		UUID idLuogo = UUID.fromString("e9352fee-97bd-48a0-9e88-82db88eebcad");
//		UUID idProdotto = UUID.fromString("3f12a440-069f-459e-9abb-5fe1702ea3d1");
//
//		Luogo luogo = luogoSrv.getLuogoByID(idLuogo);
//		Prodotto prodotto = prodottoSrv.getProdottoByID(idProdotto);
//
//		luogo.getProdotti().add(prodotto);
//		prodotto.getLuoghi().add(luogo);
//
//		luogoSrv.saveLuogo(luogo);

	}

}
