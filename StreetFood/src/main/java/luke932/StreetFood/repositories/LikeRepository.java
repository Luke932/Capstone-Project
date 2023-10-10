package luke932.StreetFood.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import luke932.StreetFood.entities.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {

	Like findByUtenteIdAndProdottoId(UUID utenteId, UUID prodottoId);

	List<Like> findByProdottoId(UUID prodottoId);

	List<Like> findByUtenteId(UUID utenteId);

	@Query("SELECT COUNT(l) FROM Like l WHERE l.prodotto.id = :prodottoId")
	Long countLikesByProdottoId(UUID prodottoId);

	@Query("SELECT COUNT(l) FROM Like l WHERE l.utente.id = :utenteId")
	Long countLikesByUtenteId(UUID utenteId);
}
