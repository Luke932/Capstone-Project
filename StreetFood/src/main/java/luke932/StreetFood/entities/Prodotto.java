package luke932.StreetFood.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "titolo", "accountNonExpired", "authorities", "credentialsNonExpired", "accountNonLocked" })
public class Prodotto {

	@Id
	@GeneratedValue
	@Column(length = 100000000)
	private UUID id;
	@Column(length = 100000000)
	private String nomeProdotto;
	@Column(length = 100000000)
	private String descrizione;
	@Column(length = 100000000)
	private String immagine;

	@ManyToMany(mappedBy = "prodotti")
	@JsonBackReference
	private List<Luogo> luoghi = new ArrayList<>();

	@OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Like> likes = new ArrayList<>();

	@OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Commento> commenti = new ArrayList<>();

	@Transient
	private String titolo;

	public Prodotto(String nomeProdotto, String descrizione, String immagine, String titolo) {
		this.nomeProdotto = nomeProdotto;
		this.descrizione = descrizione;
		this.immagine = immagine;
		this.titolo = titolo;
	}

	public Prodotto(String nomeProdotto, String descrizione, String immagine, List<Luogo> luoghi) {
		super();
		this.nomeProdotto = nomeProdotto;
		this.descrizione = descrizione;
		this.immagine = immagine;
		this.luoghi = luoghi;
	}

	@Override
	public String toString() {
		return "Prodotto [id=" + id + ", nomeProdotto=" + nomeProdotto + ", descrizione=" + descrizione + ", immagine="
				+ immagine + "]";
	}

}
