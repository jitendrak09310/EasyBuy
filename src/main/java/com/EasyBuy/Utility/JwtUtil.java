package com.EasyBuy.Utility;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.EasyBuy.Entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	private final String SECRET_KEY = "Thisismysecretkey.itisverylongwithoutspace";

	private Key getSignkey() {
		log.info("Inside JwtUtil getSignkey.. ");
		byte[] bytes = SECRET_KEY.getBytes();
		return Keys.hmacShaKeyFor(bytes);
	}

	public String generateToken(User user) {
		log.info("Inside JwtUtil generateToken.. ");
		return Jwts.builder().setSubject(user.getUserName()).claim("userId", user.getId()).claim("role", user.getRole())
				.claim("teamId", user.getTeamId()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
				.signWith(getSignkey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractUserName(String token) {
		log.info("Inside JwtUtil extractUserName.. ");
		return extractAllClaims(token).getSubject();
	}

	public Claims extractAllClaims(String token) {
		log.info("Inside JwtUtil extractAllClaims.. ");
		return Jwts.parserBuilder().setSigningKey(getSignkey()).build().parseClaimsJws(token).getBody();
	}

	public boolean isTokenValid(String userName, String token) {
		log.info("Inside JwtUtil isTokenValid.. ");
		String extractedUserName = extractUserName(token);
		return (extractedUserName.equals(userName) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) {
		log.info("Inside JwtUtil isTokenExpired.. ");
		return extractAllClaims(token).getExpiration().before(new Date());
	}
}
