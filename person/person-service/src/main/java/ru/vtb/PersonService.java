package ru.vtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
public class PersonService {

    public static void main(String[] args) {
        SpringApplication.run(PersonService.class, args);
    }
}
