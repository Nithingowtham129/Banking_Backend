package com.example.demo.config;

import java.util.Date;

import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "9kDKw8Fh@uWcZ3vXpL1sR9mGdTfBxNzQ2yEvUpJqHcKsMdPnRqTgUkXp2s5v8y/B";

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuer("OnlineBankingApp")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String userEmail) {
        String extractedEmail = extractUsername(token);
        return (extractedEmail.equals(userEmail) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
