package com.example.demo.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	

	private String secretKey;
	
	public JwtService(){
		secretKey =  generateSecretKey();
	}
	
	public String generateSecretKey() {
		
		try {
		KeyGenerator KeyGen = KeyGenerator.getInstance("HmacSHA256");
		SecretKey secretKey = KeyGen.generateKey();
		System.out.println("secret Key :"+secretKey.toString());
		return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		}catch(NoSuchAlgorithmException e) {
			throw new RuntimeException("Error generating secret Key ",e);
		}
	}
	
	
	public String generateToken(String username) {
		
		
		//A token should have head, body, signature
		Map<String, Object> claims = new HashMap<>();
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*3))
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getKey() {
		byte[] Keybytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(Keybytes );
	}

	public String extractUserName(String token) {
		return extractClaim(token , Claims::getSubject);
	}
	
	private<T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
	    return ((JwtParser) Jwts.parser()
	            .setSigningKey(getKey()))
	            .parseClaimsJws(token)
	            .getBody();
	}



	
	public boolean validateToken(String token, UserDetails userDetails) {
		
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	

	
}
