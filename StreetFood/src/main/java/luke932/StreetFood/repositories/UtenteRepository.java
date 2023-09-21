package luke932.StreetFood.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import luke932.StreetFood.entities.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
	Optional<Utente> findByEmail(String email);

	Utente findByNome(String nome);

	Utente findByUsername(String username);

	List<Utente> findByRuoloNome(String nomeRuolo);

	@Query("SELECT u FROM Utente u JOIN u.commenti c WHERE c.prodotto.id = :prodottoId")
	List<Utente> findUtentiByProdottoCommentato(UUID prodottoId);

	@Query("SELECT DISTINCT u FROM Utente u JOIN u.commenti c WHERE c.prodotto.id = :prodottoId AND u.ruolo.nome = :ruoloNome")
	List<Utente> findUtentiByProdottoCommentatoAndRuolo(UUID prodottoId, String ruoloNome);

	@Query("SELECT DISTINCT u FROM Utente u WHERE EXISTS (SELECT 1 FROM Commento c WHERE c.utente = u)")
	List<Utente> findUtentiConCommenti();

	@Query("SELECT DISTINCT u FROM Utente u WHERE EXISTS (SELECT 1 FROM Like l WHERE l.utente = u)")
	List<Utente> findUtentiConLike();

	@Query("SELECT u.foto FROM Utente u JOIN u.commenti c WHERE c.id = :commentoId")
	byte[] findFotoByCommentoId(@Param("commentoId") UUID commentoId);
}
