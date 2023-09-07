package luke932.StreetFood.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import luke932.StreetFood.entities.Commento;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, UUID> {

	@Query("SELECT COUNT(c) FROM Commento c WHERE c.prodotto.id = :prodottoId")
	Long countCommentiByProdottoId(UUID prodottoId);

	@Query("SELECT COUNT(c) FROM Commento c WHERE c.utente.id = :utenteId")
	Long countCommentiByUtenteId(UUID utenteId);

	List<Commento> findByProdottoId(UUID prodottoId);

	List<Commento> findByUtenteId(UUID utenteId);
}
