package luke932.StreetFood.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import luke932.StreetFood.entities.Utente;
import luke932.StreetFood.exceptions.UnauthorizedException;

@Component
public class JWTTools {

	@Value("${spring.jwt.secret}")
	private String secret;

	public String createToken(Utente u) {
		String token = Jwts.builder().setSubject(u.getId().toString()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
		return token;
	}

	public void verifyToken(String token) throws UnauthorizedException {
		try {
			Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);

		} catch (UnauthorizedException e) {
			System.out.println(e.getMessage());
			throw new UnauthorizedException("Il token non è valido! Per favore effettua di nuovo il login");
		}
	}

	public String extractSubject(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
				.getBody().getSubject();

	}
}
