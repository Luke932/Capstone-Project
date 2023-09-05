package luke932.StreetFood.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Ruoli")
public class Ruolo {

	@Id
	@Column(unique = true)
	private String nome;
	private Boolean ruoloUser;
	private Boolean ruoloAdmin;
}
