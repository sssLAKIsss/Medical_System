package ru.vtb.service;

import reactor.core.publisher.Mono;
import ru.vtb.dto.InfoDto;

public interface IInfoService {
    Mono<InfoDto> getAllInfo(String passportNumber);
}
