package ru.vtb.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.vtb.dto.PatientDto;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Patient;
import ru.vtb.repository.PatientRepository;
import ru.vtb.service.queue.IDataQueue;
import ru.vtb.util.JsonCreator;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvDataSender implements IScheduler {
    private final IDataQueue<Long> patientDataQueue;
    private final IModelMapper<Patient, PatientDto> patientMapper;
    private final PatientRepository patientRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String KAFKA_TOPIC = "medical_topic";
    private static final long DELAY = 1000L;

    @Override
    @Async
    @Scheduled(fixedDelay = DELAY)
    @SchedulerLock(name = "medicalDataSendTask")
    public void scheduleTask() {
        Long patientDataId = patientDataQueue.pollData();
        if (Objects.isNull(patientDataId)) return;

        Patient patient =
                patientRepository.findById(patientDataId)
                        .orElseThrow(() -> new RuntimeException("No value present"));

        kafkaTemplate.send(
                        KAFKA_TOPIC,
                        patient.getDocumentNumber(),
                        JsonCreator.getStringJsonNodeFromObject(
                                patientMapper.convertToDto(patient)
                        )
                )
                .addCallback(new ListenableFutureCallback<>() {
                                 @Override
                                 public void onFailure(Throwable ex) {
                                     log.error("Failure to send csvData");
                                     patientDataQueue.putInQueue(patientDataId);
                                 }

                                 @Override
                                 public void onSuccess(SendResult<String, String> result) {
                                     log.info("success send csvData");
                                 }
                             }
                );
    }
}
