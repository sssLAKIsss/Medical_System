package ru.vtb.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vtb.controller.api.custom.ApiParams;
import ru.vtb.controller.api.custom.ApiV1;

import javax.validation.constraints.Pattern;
import java.awt.image.BufferedImage;

@Tag(
        name = "qr-controller",
        description = "Операции с qr-кодами, получение по паспорту и верификация"
)
@Validated
@ApiParams
@ApiV1
public interface QrCodeApi {

    @Operation(
            summary = "Получить qr по номеру паспорта",
            description = "Позволяет получить qr по его уникальному номеру паспорта"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BufferedImage.class)))
    })
    @GetMapping(value = "/file", produces = MediaType.IMAGE_PNG_VALUE)
    ResponseEntity<BufferedImage> getQrCodeByPassportNumber(
            @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = "Номер документа строго 10 цифр") String passportNumber
    );

    @Operation(
            summary = "Получить base64-qrcode по номеру паспорта",
            description = "Позволяет получить base64-qrcode по его уникальному номеру паспорта"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping(value = "/base64", produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<String> getBase64QrCodeStringByPassportNumber(
            @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = "Номер документа строго 10 цифр") String passportNumber
    );

    @Operation(
            summary = "Проверка принадлежности qr-code его владельцу по паспорту",
            description = "Проверка принадлежности qr-code его владельцу по паспорту"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Boolean.class)))
    })
    @GetMapping(value = "/check")
    ResponseEntity<Boolean> checkQrCode(@RequestParam String qrCode);
}
