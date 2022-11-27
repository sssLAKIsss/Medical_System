package ru.vtb.external;

import org.springframework.cloud.openfeign.FeignClient;
import ru.vtb.personservice.client.api.PersonApiApi;

@FeignClient(name = "person-service", url = "localhost:8081")
public interface PersonsClient extends PersonApiApi {

}
