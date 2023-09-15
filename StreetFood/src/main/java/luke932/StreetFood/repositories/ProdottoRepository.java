package luke932.StreetFood.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import luke932.StreetFood.entities.Luogo;
import luke932.StreetFood.entities.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, UUID> {
	Prodotto findByNomeProdotto(String nomeProdotto);

	List<Prodotto> findByLikesUtenteId(UUID utenteId);

	@Query("SELECT DISTINCT p FROM Prodotto p JOIN p.likes l WHERE l.utente.ruolo.nome = :ruoloNome")
	List<Prodotto> findProdottiConLikeDaUtentiConRuolo(String ruoloNome);

	List<Prodotto> findByCommentiUtenteId(UUID utenteID);

	@Query("SELECT p FROM Prodotto p WHERE SIZE(p.commenti) >= :minCommenti")
	List<Prodotto> findProdottiConAlmenoNCommento(int minCommenti);

	@Query("SELECT DISTINCT p FROM Prodotto p JOIN p.commenti l WHERE l.utente.ruolo.nome = :ruoloNome")
	List<Prodotto> findProdottiConCommentoDaUtentiConRuolo(String ruoloNome);

	@Query(value = "SELECT p FROM Prodotto p JOIN p.luoghi luoghi JOIN p.likes likes WHERE luoghi.id = :luogoId GROUP BY p.id HAVING COUNT(likes.id) >= :minLikes")
	List<Prodotto> findProdottiByLuogoAndMinLikes(@Param("luogoId") UUID luogoId, @Param("minLikes") int minLikes);

	List<Prodotto> findByLuoghi(Luogo luogo);

	@Query("SELECT p FROM Prodotto p JOIN p.luoghi luogo WHERE p.nomeProdotto = :nome AND luogo.id IN :luoghiIds")
	List<Prodotto> findProdottiByNomeAndLuoghi(@Param("nome") String nomeProdotto,
			@Param("luoghiIds") List<UUID> luoghiIds);

	@Query("SELECT p FROM Prodotto p JOIN p.likes likes GROUP BY p.id HAVING COUNT(likes.id) = :numLikes")
	List<Prodotto> findProdottiByNumLikes(@Param("numLikes") int numLikes);

	@Query("SELECT p FROM Prodotto p JOIN p.luoghi luogo JOIN p.likes likes WHERE luogo.id IN :luoghiIds GROUP BY p.id HAVING COUNT(likes.id) = :numLikes")
	List<Prodotto> findProdottiByLuoghiAndNumLikes(@Param("luoghiIds") List<UUID> luoghiIds,
			@Param("numLikes") int numLikes);

}
