package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.QrCode;
import ru.vtb.repository.QrCodeRepository;
import ru.vtb.service.IQrCodeService;
import ru.vtb.util.QrCodeUtil;

import java.awt.image.BufferedImage;

@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements IQrCodeService {
    private final QrCodeRepository qrCodeRepository;

    @Override
    @Transactional(readOnly = true)
    public BufferedImage getQrCodeByPassportNumber(String passportNumber) {
        QrCode qrCode = qrCodeRepository.findByPassportNumber(passportNumber)
                .orElseThrow(() -> new RuntimeException("Нету данных этого чела"));

        return QrCodeUtil.convertBase64DataToBufferedImage(qrCode.getCrCode());
    }

    @Override
    @Transactional(readOnly = true)
    public String getBase64StringQrCodeByPassportNumber(String passportNumber) {
        return (qrCodeRepository.findByPassportNumber(passportNumber)
                .orElseThrow(() -> new RuntimeException("Нету данных этого чела")))
                .getCrCode();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkQrCode(BufferedImage qrCode) {
        return qrCodeRepository.existsQrCodeByCrCode(
                QrCodeUtil.convertBufferedImageToBase64Data(qrCode)
        );
    }
}
