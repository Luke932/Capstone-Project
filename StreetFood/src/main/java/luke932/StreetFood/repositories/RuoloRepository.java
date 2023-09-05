package luke932.StreetFood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import luke932.StreetFood.entities.Ruolo;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, String> {

}
