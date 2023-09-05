package luke932.StreetFood.entities;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Utente {

	@Id
	@GeneratedValue
	private UUID id;
	private String username;
	private String nome;
	private String cognome;
	private String email;
	private String password;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Ruolo", referencedColumnName = "nome")
	private Ruolo ruolo;

	@OneToMany(mappedBy = "utente")
	private Set<Like> likes;

	@OneToMany(mappedBy = "utente")
	private Set<Commento> commenti;

	public Utente() {
		this.ruolo = new Ruolo();
		this.ruolo.setNome("User");
	}

}
