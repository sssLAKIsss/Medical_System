package ru.vtb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.vtb.model.QrCode;
import ru.vtb.repository.QrCodeRepository;
import ru.vtb.service.IMedicalDataListener;
import ru.vtb.util.QrCodeUtil;

@Service
@RequiredArgsConstructor
public class MedicalDataListener implements IMedicalDataListener {
    private final QrCodeRepository qrCodeRepository;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows(JsonProcessingException.class)
    @KafkaListener(topics = "#{'${kafka.topics}'.split(',')}")
    public void saveVaccinationDataFromKafka(String vaccinationDto) {
        if (StringUtils.isBlank(vaccinationDto)) return;

        JsonNode vaccinationData = objectMapper.readTree(vaccinationDto);

        Long qrCodeId = qrCodeRepository
                .findByPassportNumber(vaccinationData.get("documentNumber")
                        .asText())
                .map(QrCode::getId)
                .orElse(null);

        qrCodeRepository.save(
                QrCode.builder()
                        .id(qrCodeId)
                        .passportNumber(vaccinationData.get("documentNumber").asText())
                        .value(vaccinationDto)
                        .qrCode(
                                QrCodeUtil.convertBufferedImageToBase64Data(
                                        QrCodeUtil.createBufferedImageFromString(
                                                vaccinationData.toString()
                                        ))
                        )
                        .build()
        );
    }
}
