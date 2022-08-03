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

	@SuppressWarnings("deprecation")
	public static String generateToken(String name, String scope) {
		String id = UUID.randomUUID().toString().replace("-", "");
		Calendar cal = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
		cal2.add(Calendar.DATE, 1);

		String token = Jwts.builder().setId(id)
				.setIssuer("beratungsprotokoll")
				.setSubject("token")
				.claim("name", name)
				.claim("scope", scope)
				.setIssuedAt(cal.getTime())
				.setNotBefore(cal.getTime())
				.setExpiration(cal2.getTime())
				.signWith(SignatureAlgorithm.HS256, base64SecretBytes).compact();

		return token;
	}

	@SuppressWarnings("deprecation")
	public static Claims verifyToken(String token) throws Exception{
		return Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(token).getBody();
	}
}