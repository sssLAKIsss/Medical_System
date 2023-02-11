package ru.vtb.util;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vtb.config.properties.JwtProperties;
import ru.vtb.model.User;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties properties;
    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(properties.getSecret().getBytes());
    }

    public String extractUserName(String authToken) {
        return getClaimsFromToken(authToken).getSubject();
    }

    public boolean validateToken(String authToken) {
        return getClaimsFromToken(authToken).getExpiration().after(Date.from(Instant.now()));
    }

    public Claims getClaimsFromToken(String authToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    public String createToken(User user) {
        return Jwts.builder()
                .setClaims(Map.of("role", List.of(user.getRole())))
                .setSubject(user.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(
                                Instant.now()
                                        .plus(Long.parseLong(properties.getExpirationToken()), ChronoUnit.SECONDS)))
                .signWith(key)
                .compact();
    }
}
