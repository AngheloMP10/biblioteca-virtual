package com.biblio.virtual.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration; // en milisegundos

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	// Generar token con username y rol formateado correctamente
	public String generateToken(String username, String role) {
		if (role != null && !role.startsWith("ROLE_")) {
			role = "ROLE_" + role; // garantiza formato correcto
		}

		return Jwts.builder().setIssuer("biblioteca-virtual").setSubject(username).claim("role", role)
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	// Extraer username del token
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	// Extraer rol del token
	public String extractRole(String token) {
		String role = extractAllClaims(token).get("role", String.class);
		if (role != null && !role.startsWith("ROLE_")) {
			role = "ROLE_" + role;
		}
		return role;
	}

	// Extraer fecha de expiración
	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	// Validar token (usuario correcto y no expirado)
	public Boolean validateToken(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
}
