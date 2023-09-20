package luke932.StreetFood.entities;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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
	@Lob
	private byte[] immagine;
	private String descrizione;

	@ManyToMany
	@JsonBackReference
	@JoinTable(name = "luogo_prodotto", joinColumns = @JoinColumn(name = "luogo_id"), inverseJoinColumns = @JoinColumn(name = "prodotto_id"))
	private List<Prodotto> prodotti = new ArrayList<>();

	public String getImmagineBase64() {
		return Base64.getEncoder().encodeToString(this.immagine);
	}

	public void setImmagineBase64(String immagineBase64) {
		this.immagine = Base64.getDecoder().decode(immagineBase64);
	}
}
