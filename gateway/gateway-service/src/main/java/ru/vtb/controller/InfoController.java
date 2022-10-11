package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.vtb.dto.InfoDto;
import ru.vtb.service.InfoServiceImpl;

@RestController
@RequestMapping("/api/v1/gateway")
@RequiredArgsConstructor
public class InfoController {
    private final InfoServiceImpl infoService;

    @GetMapping("/{passportNumber}")
    public ResponseEntity<Mono<InfoDto>> getAllPersonInformation(@PathVariable String passportNumber) {
        return ResponseEntity.ok(infoService.getAllInfo(passportNumber));
    }
}
