package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.vtb.model.User;
import ru.vtb.service.UserService;
import ru.vtb.util.JwtUtil;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private static final ResponseEntity<Objects> UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    @PostMapping("/login/{login}/{password}")
    public Mono<ResponseEntity<?>> getToken(@PathVariable String login,
                                            @PathVariable String password) {
        return userService.findByUsername(login)
                .cast(User.class)
                .map(user ->
                        Objects.equals(
                                password,
                                user.getPassword()
                        )
                                ? ResponseEntity.ok(jwtUtil.createToken(user))
                                : UNAUTHORIZED
                )
                .defaultIfEmpty(UNAUTHORIZED);
    }
}
