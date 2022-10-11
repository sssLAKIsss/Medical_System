package ru.vtb.service;

import java.awt.image.BufferedImage;

public interface IQrCodeService {
    BufferedImage getQrCodeByPassportNumber(String passportNumber);
    String getBase64StringQrCodeByPassportNumber(String passportNumber);
    boolean checkQrCode(BufferedImage qrCode);

}
