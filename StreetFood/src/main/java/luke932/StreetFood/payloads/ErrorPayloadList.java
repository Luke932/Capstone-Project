package luke932.StreetFood.payloads;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorPayloadList {
	private String message;
	private LocalDate timestampp;
	private List<String> errorsList;
}
