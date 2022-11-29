package ru.vtb.external;

import org.springframework.cloud.openfeign.FeignClient;
import ru.vtb.personservice.client.api.PersonApiApi;

@FeignClient(
        name = "${external.client.person-service-client.service-name}",
        url = "${external.client.person-service-client.base-url}")
public interface PersonsClient extends PersonApiApi {
}
