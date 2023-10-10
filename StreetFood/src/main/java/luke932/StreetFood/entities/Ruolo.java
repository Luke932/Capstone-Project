package luke932.StreetFood.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "Ruoli")
public class Ruolo {

	@Id
	@GeneratedValue
	private UUID id;
	@Column(unique = true)
	private String nome;
	private Boolean ruoloUser;
	private Boolean ruoloAdmin;

	public Ruolo(String nome, Boolean ruoloUser, Boolean ruoloAdmin) {
		super();
		this.nome = nome;
		this.ruoloUser = ruoloUser;
		this.ruoloAdmin = ruoloAdmin;
	}

}
