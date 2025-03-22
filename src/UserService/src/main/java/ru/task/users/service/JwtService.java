package ru.task.users.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import ru.task.users.models.User;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String secretKey;

    public String generateToken(User user) {
        return Jwts.builder()
                .setClaims(createClaims(user))
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Claims createClaims(User user) {
        Claims claims = Jwts.claims();
        claims.put("id", (user.getId()));
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        return claims;
    }
}