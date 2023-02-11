package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.vtb.controller.api.UserApi;
import ru.vtb.dto.LogInDto;
import ru.vtb.dto.UserDto;
import ru.vtb.model.User;
import ru.vtb.service.UserService;
import ru.vtb.util.JwtUtil;


@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final ResponseEntity<Object> UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    public Mono<ResponseEntity<?>> getToken(LogInDto logInDto) {
        return userService.findByUsername(logInDto.getUserLogin())
                .cast(User.class)
                .map(user ->
                        passwordEncoder.matches(logInDto.getUserPassword(), user.getPassword())
                                ? ResponseEntity.ok(jwtUtil.createToken(user))
                                : UNAUTHORIZED
                )
                .defaultIfEmpty(UNAUTHORIZED)
                .log();
    }

    public Mono<ResponseEntity<String>> createUser(UserDto userDto) {
        return userService.createUser(userDto)
                .map(d -> ResponseEntity.status(HttpStatus.CREATED).body(userDto.getUsername()))
                .log();
    }
}
