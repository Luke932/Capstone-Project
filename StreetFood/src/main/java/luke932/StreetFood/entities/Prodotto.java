package luke932.StreetFood.entities;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Prodotto {

	@Id
	@GeneratedValue
	private UUID id;

	private String nomeProdotto;
	private String Descrizione;
	private String immagine;
	private String altro;

	@ManyToOne
	@JoinColumn(name = "luogo_id")
	private Luogo luogo;

	@OneToMany(mappedBy = "prodotto")
	private Set<Like> likes;

	@OneToMany(mappedBy = "prodotto")
	private Set<Commento> commenti;
}
