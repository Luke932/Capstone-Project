package luke932.StreetFood.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Luogo {

	@Id
	@GeneratedValue
	@Column(length = 100000000)
	private UUID id;
	@Column(length = 100000000)
	private String titolo;
	@Column(length = 100000000)
	private String immagine;
	@Column(length = 100000000)
	private String descrizione;

	@ManyToMany
	@JsonBackReference
	@JoinTable(name = "luogo_prodotto", joinColumns = @JoinColumn(name = "luogo_id"), inverseJoinColumns = @JoinColumn(name = "prodotto_id"))
	private List<Prodotto> prodotti = new ArrayList<>();

}
