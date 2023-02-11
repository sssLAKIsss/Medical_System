package ru.vtb.config.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.vtb.util.JwtUtil;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtUtil jwtUtil;

    @SuppressWarnings("unchecked")
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtUtil.extractUserName(authToken);
        return Mono.just(jwtUtil.validateToken(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(v -> {
                    Claims claims = jwtUtil.getClaimsFromToken(authToken);
                    List<String> roles = claims.get("role", List.class);
                    List<SimpleGrantedAuthority> authorities = roles
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();
                    return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );
                });
    }
}
