package ru.vtb.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import ru.vtb.controller.api.custom.ApiParams;
import ru.vtb.controller.api.custom.ApiV1;
import ru.vtb.dto.LogInDto;
import ru.vtb.dto.UserDto;

import javax.validation.Valid;

@Tag(
        name = "user - controller",
        description = "API для регистрации и получения токена "
)
@Validated
@ApiParams
@ApiV1
public interface UserApi {

    @Operation(
            summary = "Получить jwt-token",
            description = "Позволяет получить jwt-token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/tokens")
    Mono<ResponseEntity<?>> getToken(@RequestBody @Valid LogInDto logInDto);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Позволяет пользователю с ролью \"Admin\" добавить в базу нового пользователя",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping( "/registration")
    Mono<ResponseEntity<String>> createUser(@RequestBody @Valid UserDto userDto);
}