package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.controller.api.QrCodeApi;
import ru.vtb.service.IQrCodeService;

import java.awt.image.BufferedImage;

@RestController
@RequiredArgsConstructor
public class QrCodeController implements QrCodeApi {
    private final IQrCodeService qrCodeService;

    @Override
    public ResponseEntity<BufferedImage> getQrCodeByPassportNumber(String passportNumber) {
        return ResponseEntity.ok(qrCodeService.getQrCodeByPassportNumber(passportNumber));
    }

    @Override
    public ResponseEntity<String> getBase64QrCodeStringByPassportNumber(String passportNumber) {
        return ResponseEntity.ok(qrCodeService.getBase64StringQrCodeByPassportNumber(passportNumber));
    }

    @Override
    public ResponseEntity<Boolean> checkQrCode(String qrCode) {
        return ResponseEntity.ok(qrCodeService.checkQrCode(qrCode));
    }
}
