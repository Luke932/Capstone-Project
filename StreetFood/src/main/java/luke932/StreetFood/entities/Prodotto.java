package luke932.StreetFood.entities;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Prodotto {

	@Id
	@GeneratedValue
	private UUID id;

	private String nomeProdotto;
	private String Descrizione;
	private String immagine;
	private String altro;

	@OneToMany(mappedBy = "prodotto")
	private Set<Like> likes;

	@OneToMany(mappedBy = "prodotto")
	private Set<Commento> commenti;
}
