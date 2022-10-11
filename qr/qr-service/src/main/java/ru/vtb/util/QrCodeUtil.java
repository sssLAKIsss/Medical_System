package ru.vtb.util;

import lombok.experimental.UtilityClass;
import net.glxn.qrgen.javase.QRCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@UtilityClass
public class QrCodeUtil {

    public BufferedImage createBufferedImageFromString(String data) {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(
                QRCode
                        .from(data)
                        .withSize(250, 250)
                        .stream()
                        .toByteArray()
        )) {
            return ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertBufferedImageToBase64Data(BufferedImage image) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", byteArrayOutputStream);
            return Base64.getEncoder()
                    .encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage convertBase64DataToBufferedImage(String base64String) {
        try (ByteArrayInputStream inputStream =
                     new ByteArrayInputStream(Base64.getDecoder().decode(base64String))) {
            return  ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
