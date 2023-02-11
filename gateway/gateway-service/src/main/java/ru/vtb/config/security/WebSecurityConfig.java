package ru.vtb.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers("/webjars/**","/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .pathMatchers("/api/v1/aggregator/tokens/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                                )
                )
                .accessDeniedHandler(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                                )
                )
                .and()
                .build();
    }
}
