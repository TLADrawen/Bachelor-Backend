package com.okv.protokoll;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;


public class JWTUtils {
	private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
	private static final byte[] secretBytes = secret.getEncoded();
	private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);

	/**
	 * Generiert einen JWT. Gedacht für den Login Service
	 * @param name Benutzername des eingeloggten Nutzers.
	 * @param scope Berechtigungen des Nutzers. ("nutzer" oder "admin")
	 * @return Verschlüsselter JWT als String
	 */
	@SuppressWarnings("deprecation")
	public static String generateToken(String name, String scope) {
		String id = UUID.randomUUID().toString().replace("-", "");
		Calendar cal = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
		cal2.add(Calendar.DATE, 1);

		String token = Jwts.builder().setId(id)
				.setIssuer("protokoll")
				.setSubject("token")
				.claim("name", name)
				.claim("scope", scope)
				.setIssuedAt(cal.getTime()) //aktueller Zeitpunkt
				.setNotBefore(cal.getTime()) //aktueller Zeitpunkt
				.setExpiration(cal2.getTime()) //In einem Tag
				.signWith(SignatureAlgorithm.HS256, base64SecretBytes).compact();

		return token;
	}

	/**
	 * 
	 * @param token JWT token welcher verifiziert werden soll.
	 * @return Claims, welche unter "name" den Benutzernamen enthalten und unter "scope" die Berechtigungen als Strings
	 * @throws Exception Wenn der Token nicht verifiziert werden kann.
	 */
	@SuppressWarnings("deprecation")
	public static Claims verifyToken(String token) throws Exception{
		return Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(token).getBody();
	}
}