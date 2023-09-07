package luke932.StreetFood.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "titolo", "accountNonExpired", "authorities", "credentialsNonExpired", "accountNonLocked" })
public class Prodotto {

	@Id
	@GeneratedValue
	private UUID id;

	private String nomeProdotto;
	private String descrizione;
	private String immagine;
	private String altro;

	@ManyToOne
	@JoinColumn(name = "luogo_id")
	@JsonBackReference
	private Luogo luogo;

	@OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Like> likes;

	@OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Commento> commenti;

	@Transient
	private String titolo;

	public Prodotto(String nomeProdotto, String descrizione, String immagine, String altro, Luogo luogo) {
		this.nomeProdotto = nomeProdotto;
		this.descrizione = descrizione;
		this.immagine = immagine;
		this.altro = altro;
		this.luogo = luogo;
	}

	public Prodotto(String nomeProdotto, String descrizione, String immagine, String altro, String titolo) {
		this.nomeProdotto = nomeProdotto;
		this.descrizione = descrizione;
		this.immagine = immagine;
		this.altro = altro;
		this.titolo = titolo;
	}

}
