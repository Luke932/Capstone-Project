package luke932.StreetFood.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import luke932.StreetFood.entities.Ruolo;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, String> {
	Ruolo findByNome(String nome);

	Optional<Ruolo> findById(String sigla);
}
