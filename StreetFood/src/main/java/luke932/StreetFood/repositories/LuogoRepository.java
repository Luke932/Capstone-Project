package luke932.StreetFood.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import luke932.StreetFood.entities.Luogo;

@Repository
public interface LuogoRepository extends JpaRepository<Luogo, UUID> {

	Luogo findByTitolo(String titolo);

	List<Luogo> findByDescrizioneContaining(String descrizione);

	@Query("SELECT DISTINCT l FROM Luogo l JOIN l.prodotti p WHERE p.nomeProdotto = :nomeProdotto")
	List<Luogo> findLuoghiConProdotto(String nomeProdotto);
}
