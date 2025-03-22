package ru.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import ru.gateway.exceptions.InvalidJwtTokenException;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String secretKey;

    public Long getUserIdFromAuthHeader(String authHeader) {
        Claims claims = getClaims(authHeader);
        return claims.get("id", Long.class);
    }

    public boolean isTokenExpired(String authHeader) {
        Claims claims = getClaims(authHeader);
        return claims.getExpiration().before(new Date());
    }

    public String getUserRoleFromAuthHeader(String authHeader) {
        Claims claims = getClaims(authHeader);
        return claims.get("role", String.class);
    }

    private Claims getClaims(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw new InvalidJwtTokenException();
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(authHeader.substring(7))
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
