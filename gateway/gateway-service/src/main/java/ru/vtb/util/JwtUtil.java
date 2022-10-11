package ru.vtb.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vtb.model.User;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expirationToken}")
    private String expirationToken;

    public String extractUserName(String authToken) {
        return getClaimsFromToken(authToken).getSubject();
    }

    public boolean validateToken(String authToken) {
        return getClaimsFromToken(authToken).getExpiration().getTime() > new Date().getTime();
    }

    public Claims getClaimsFromToken(String authToken) {
        String key = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    public String createToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", List.of(user.getRole()));

        long expirationTime = Long.parseLong(expirationToken);
        Date creation = new Date();
        Date expiration = new Date(creation.getTime() + expirationTime * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(creation)
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }
}
