package ru.vtb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vtb.config.properties.integration.MedicalServiceProperties;
import ru.vtb.config.properties.integration.PersonServiceProperties;
import ru.vtb.config.properties.integration.QrServiceProperties;
import ru.vtb.dto.InfoDto;
import ru.vtb.medicalservice.client.model.PatientDto;
import ru.vtb.personservice.client.model.PersonDto;


@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements IInfoService {
    private final WebClient.Builder webClientBuilder;
    private final PersonServiceProperties personProps;
    private final MedicalServiceProperties medicalProps;
    private final QrServiceProperties qrProps;

    @Override
    public Mono<InfoDto> getAllInfo(String passportNumber) {
        Mono<PersonDto> fromPersonService = webClientBuilder
                .build()
                .get()
                .uri(personProps.getBaseUri().concat(personProps.getPaths().getPassportPath()), passportNumber)
                .retrieve().bodyToMono(PersonDto.class).onErrorReturn(new PersonDto()).log();
        Mono<PatientDto> fromMedicalService = webClientBuilder
                .build()
                .get()
                .uri(medicalProps.getBaseUri().concat(medicalProps.getPaths().getPassportPath()), passportNumber)
                .retrieve().bodyToMono(PatientDto.class).onErrorReturn(new PatientDto()).log();
        Mono<String> fromQrService = webClientBuilder
                .build()
                .get()
                .uri(qrProps.getBaseUri().concat(qrProps.getPaths().getPassportPath()), passportNumber)
                .retrieve().bodyToMono(String.class).onErrorReturn("").log();

        return Mono.zip(fromQrService, fromMedicalService, fromPersonService)
                .map(tuple3 -> InfoDto.builder()
                        .patientDto(tuple3.getT2())
                        .personDto(tuple3.getT3())
                        .bufferedImage(tuple3.getT1())
                        .build())
                .log();
    }
}
