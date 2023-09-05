package luke932.StreetFood.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import luke932.StreetFood.entities.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, UUID> {
	Prodotto findByNomeProdotto(String nomeProdotto);
}
