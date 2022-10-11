package ru.vtb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.personservice.client.model.ApiError;
import ru.vtb.service.IQrCodeService;

import java.awt.image.BufferedImage;

@Tag(
        name = "qr-controller",
        description = "Операции с qr-кодами, получение по паспорту и верификация"
)
@RestController
@RequestMapping("/api/v1/qr")
@RequiredArgsConstructor
public class QrCodeController {
    private final IQrCodeService qrCodeService;

    @Operation(
            summary = "Получить qr по номеру паспорта",
            description = "Позволяет получить qr по его уникальному номеру паспорта"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = BufferedImage.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping(value = "/file/{passportNumber}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> getQrCodeByPassportNumber(@PathVariable String passportNumber) {
        return ResponseEntity.ok(qrCodeService.getQrCodeByPassportNumber(passportNumber));
    }

    @GetMapping(value = "/json/{passportNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStringByPassportNumber(@PathVariable String passportNumber) {
        return ResponseEntity.ok(qrCodeService.getBase64StringQrCodeByPassportNumber(passportNumber));
    }

    @GetMapping(value = "/check", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> getStringByPassportNumber(@RequestParam BufferedImage qrCode) {
        return ResponseEntity.ok(qrCodeService.checkQrCode(qrCode));
    }
}
