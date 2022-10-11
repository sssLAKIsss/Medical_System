package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.model.QrCode;

import java.util.Optional;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
    Optional<QrCode> findByPassportNumber(String passportNumber);
    boolean existsQrCodeByCrCode(String base64StringCode);
}
