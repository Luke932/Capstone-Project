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
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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

	@Transient
	private String ruoloNome;

	public Utente(String username, String nome, String cognome, String email, String password) {
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.ruolo = new Ruolo();
		this.ruolo.setNome("USER");
	}

	public Utente(String username, String password, String email, String nome, String cognome, String ruoloNome) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.ruoloNome = ruoloNome;
	}

}
