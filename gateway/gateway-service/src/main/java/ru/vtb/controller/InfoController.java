package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.vtb.controller.api.InfoApi;
import ru.vtb.dto.InfoDto;
import ru.vtb.service.InfoServiceImpl;

@RestController
@RequiredArgsConstructor
public class InfoController implements InfoApi {
    private final InfoServiceImpl infoService;

    public ResponseEntity<Mono<InfoDto>> getAllPersonInformation(String passportNumber) {
        return ResponseEntity.ok(infoService.getAllInfo(passportNumber));
    }
}
