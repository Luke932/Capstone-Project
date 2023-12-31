package luke932.StreetFood.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.exceptions.NotFoundException;
import luke932.StreetFood.services.FileService;
import luke932.StreetFood.services.UtenteService;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
	private final UtenteService utenteService;
	private final PasswordEncoder bcrypt;
	private final FileService fileService;

	public UtenteController(UtenteService utenteService, PasswordEncoder bcrypt, FileService fileService) {
		this.utenteService = utenteService;
		this.bcrypt = bcrypt;
		this.fileService = fileService;

	}

	// --------------- POST UTENTE USER
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ADMIN')")
	public Utente saveUser(@RequestPart("file") MultipartFile file, @RequestPart("user") Utente body)
			throws IOException {

		if (file != null && !file.isEmpty()) {
			byte[] fileData = file.getBytes();
			body.setFoto(fileData);

			// Salva il file utilizzando il servizio fileService
			String fileName = file.getOriginalFilename();
			fileService.saveFile(fileName, fileData);
		}

		body.setPassword(bcrypt.encode(body.getPassword()));
		Utente created = utenteService.save(body);

		return created;
	}

	// --------------- GET UTENTI
	@GetMapping
	// @PreAuthorize("hasAuthority('ADMIN')")
	public List<Utente> getUsers() {
		return utenteService.findNoPage();
	}

	// --------------- GET UTENTE BY ID
	@GetMapping("/{userId}")
	public Utente getUserById(@PathVariable UUID userId) {
		return utenteService.findById(userId);
	}

	// --------------- PUT UTENTE
//	@PutMapping("/{userId}")
//	public Utente updateUser(@PathVariable UUID userId, @RequestBody UtenteUpdatePayload body) {
//		body.setPassword(bcrypt.encode(body.getPassword()));
//		Utente created = utenteSrv.createUser(body);
//
//		return created;
//	}

	// --------------- DELETE UTENTE
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		utenteService.deleteUtente(userId);
	}

	// -----------------RICERCA PER USERNAME
	@GetMapping("/username/{username}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Utente getUtenteByUsername(@PathVariable String username) {
		return utenteService.findByUsername(username);
	}

	// -----------------RICERCA TUTTI GLI UTENTI CHE HANNO COMMENTATO UN DETERMINATO
	// PRODOTTO
	@GetMapping("/commentati/{prodottoId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Utente> getUtentiByProdottoCommentato(@PathVariable UUID prodottoId) {
		List<Utente> utenti = utenteService.findUtentiByProdottoCommentato(prodottoId);
		if (utenti.isEmpty()) {
			throw new NotFoundException("Nessun utente ha commentato il prodotto con ID " + prodottoId);
		}
		return utenti;
	}

	// -----------------RICERCA TUTTI GLI UTENTI CHE HANNO COMMENTATO UN DETERMINATO
	// PRODOTTO E CHE HANNO UN CERTO RUOLO
	@GetMapping("/commentati/{prodottoId}/{ruoloNome}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Utente> getUtentiByProdottoCommentatoAndRuolo(@PathVariable UUID prodottoId,
			@PathVariable String ruoloNome) {
		List<Utente> utenti = utenteService.findUtentiByProdottoCommentatoAndRuolo(prodottoId, ruoloNome);
		if (utenti.isEmpty()) {
			throw new NotFoundException(
					"Nessun utente ha commentato il prodotto con ID " + prodottoId + " e ha il ruolo " + ruoloNome);
		}
		return utenti;
	}

	// -----------------RICERCA TUTTI GLI UTENTI CHE HANNO COMMENTATO ALMENO UN
	// PRODOTTO
	@GetMapping("/con-commenti")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Utente> getUtentiConCommenti() {
		return utenteService.findUtentiConCommenti();
	}

	// -----------------RICERCA TUTTI GLI UTENTI CHE HANNO MESSO LIKE AD ALMENO UN
	// PRODOTTO
	@GetMapping("/con-like")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Utente> getUtentiConLike() {
		return utenteService.findUtentiConLike();
	}

	// -----------------RECUPERO LA FOTO DELL'UTENTE TRAMITE L'ID DEL COMMENTO
	@GetMapping(value = "/{commentoId}/fotoUtente", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getFotoUtenteByCommentoId(@PathVariable UUID commentoId) {
		return utenteService.getFotoUtenteByCommentoId(commentoId);
	}

}
