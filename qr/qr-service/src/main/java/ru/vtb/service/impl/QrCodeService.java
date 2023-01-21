package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.exception.DataNotFoundException;
import ru.vtb.model.QrCode;
import ru.vtb.repository.QrCodeRepository;
import ru.vtb.service.IQrCodeService;
import ru.vtb.util.QrCodeUtil;

import java.awt.image.BufferedImage;

@Service
@RequiredArgsConstructor
public class QrCodeService implements IQrCodeService {
    private final QrCodeRepository qrCodeRepository;

    @Override
    @Transactional(readOnly = true)
    public BufferedImage getQrCodeByPassportNumber(String passportNumber) {
        QrCode qrCode = qrCodeRepository.findByPassportNumber(passportNumber)
                .orElseThrow(DataNotFoundException::new);

        return QrCodeUtil.convertBase64DataToBufferedImage(qrCode.getQrCode());
    }

    @Override
    @Transactional(readOnly = true)
    public String getBase64StringQrCodeByPassportNumber(String passportNumber) {
        return qrCodeRepository.findByPassportNumber(passportNumber)
                .orElseThrow(DataNotFoundException::new)
                .getQrCode();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkQrCode(String qrCode) {
        return qrCodeRepository.existsQrCodeByQrCode(qrCode);
    }
}
