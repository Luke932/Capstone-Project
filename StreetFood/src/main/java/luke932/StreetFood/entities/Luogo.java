package luke932.StreetFood.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Luogo {

	@Id
	@GeneratedValue
	private UUID id;
	private String titolo;
	private String immagine;
	private String descrizione;

	@OneToMany(mappedBy = "luogo")
	private Set<Prodotto> prodotti = new HashSet<>();
}
