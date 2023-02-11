package ru.vtb.controller.api.custom;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import ru.vtb.personservice.client.model.ApiError;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, TYPE})
@Retention(RUNTIME)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Bad request",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))
            ),
        @ApiResponse(responseCode = "401", description = "Unauthorized ",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))
            ),
        @ApiResponse(responseCode = "403", description = "Forbidden",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))
            ),
        @ApiResponse(responseCode = "404", description = "Not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))
            ),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))
            )
})
public @interface ApiParams {
}
