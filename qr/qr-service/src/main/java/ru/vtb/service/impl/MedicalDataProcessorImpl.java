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
import ru.vtb.service.IMedicalDataProcessor;
import ru.vtb.util.QrCodeUtil;

@Service
@RequiredArgsConstructor
public class MedicalDataProcessorImpl implements IMedicalDataProcessor {
    private final QrCodeRepository qrCodeRepository;
    private final ObjectMapper objectMapper;

    private static final String KAFKA_TOPIC = "medical_topic";

    @Override
    @SneakyThrows(JsonProcessingException.class)
    @KafkaListener(topics = KAFKA_TOPIC)
    public void saveVaccinationDataFromKafka(String vaccinationDto) {
        if (StringUtils.isBlank(vaccinationDto)) return;

        JsonNode vaccinationData = objectMapper.readTree(vaccinationDto);

        //todo как основные правки доделаю - сделать тут цепочку валидаторов
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
                        .crCode(
                                QrCodeUtil.convertBufferedImageToBase64Data(
                                        QrCodeUtil.createBufferedImageFromString(
                                                vaccinationData.toString()
                                        ))
                        )
                        .build()
        );
    }
}
