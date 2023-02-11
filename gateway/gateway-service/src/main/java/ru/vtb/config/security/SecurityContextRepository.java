package ru.vtb.config.security;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private final AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new IllegalArgumentException("Method not supported!");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(autHeader -> autHeader.startsWith("Bearer "))
                .flatMap(authHeader -> {
                    String authToken = authHeader.substring(7);
                    return authenticationManager.authenticate(
                                    new UsernamePasswordAuthenticationToken(
                                            authToken,
                                            authToken
                                    )
                            )
                            .map(SecurityContextImpl::new);
                });
    }
}
