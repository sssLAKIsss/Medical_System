package ru.vtb.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import ru.vtb.controller.api.custom.ApiParams;
import ru.vtb.controller.api.custom.ApiV1;
import ru.vtb.dto.InfoDto;

import javax.validation.constraints.Pattern;

@Tag(
        name = "info - controller",
        description = "Позволяет получить данные клиента, его мед карту и qr-code"
)
@Validated
@ApiParams
@ApiV1
public interface InfoApi {

    @Operation(
            summary = "Получить данные о пользователе из справочника пользователей," +
                    "медицинских данных из медицинского справочника " +
                    "и qr-code из справочника qr-кодов(в формате base64)",
            description = "Позволяет получить все данные пользователя",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = InfoDto.class)))
    })
    @GetMapping
    ResponseEntity<Mono<InfoDto>> getAllPersonInformation(
            @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = "Номер документа строго 10 цифр") String passportNumber
    );
}
