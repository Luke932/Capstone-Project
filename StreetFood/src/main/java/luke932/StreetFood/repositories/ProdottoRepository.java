package luke932.StreetFood.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import luke932.StreetFood.entities.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, UUID> {
	Prodotto findByNomeProdotto(String nomeProdotto);

	List<Prodotto> findByLuogoId(UUID luogoId);

	List<Prodotto> findByLikesUtenteId(UUID utenteId);

	@Query("SELECT p FROM Prodotto p WHERE p.luogo.id = :luogoId AND SIZE(p.likes) >= :minLikes")
	List<Prodotto> findByLuogoAndMinLikes(@Param("luogoId") UUID luogoId, @Param("minLikes") int minLikes);

	@Query("SELECT p FROM Prodotto p WHERE SIZE(p.likes) >= :minLikes")
	List<Prodotto> findProdottiConAlmenoNLike(int minLikes);

	@Query("SELECT DISTINCT p FROM Prodotto p JOIN p.likes l WHERE l.utente.ruolo.nome = :ruoloNome")
	List<Prodotto> findProdottiConLikeDaUtentiConRuolo(String ruoloNome);

	List<Prodotto> findByCommentiUtenteId(UUID utenteID);

	@Query("SELECT p FROM Prodotto p WHERE p.luogo.id = :luogoId AND SIZE(p.commenti) >= :minCommenti")
	List<Prodotto> findByLuogoAndMinCommenti(@Param("luogoId") UUID luogoId, @Param("minCommenti") int minCommenti);

	@Query("SELECT p FROM Prodotto p WHERE SIZE(p.commenti) >= :minCommenti")
	List<Prodotto> findProdottiConAlmenoNCommento(int minCommenti);

	@Query("SELECT DISTINCT p FROM Prodotto p JOIN p.commenti l WHERE l.utente.ruolo.nome = :ruoloNome")
	List<Prodotto> findProdottiConCommentoDaUtentiConRuolo(String ruoloNome);
}
