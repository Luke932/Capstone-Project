package luke932.StreetFood.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Ruoli")
public class Ruolo {

	@Id
	@Column(unique = true)
	private String nome;
	private Boolean ruoloUser;
	private Boolean ruoloAdmin;
}
