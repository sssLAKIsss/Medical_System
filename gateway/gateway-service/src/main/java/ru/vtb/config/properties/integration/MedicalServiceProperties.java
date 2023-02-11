package ru.vtb.config.properties.integration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ru.vtb.config.properties.integration.params.MedicalServiceParams;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "integration.medical-service")
@Getter
@Setter
@Validated
public class MedicalServiceProperties extends IntegrationCommonProperties {

    @Valid
    @NotNull
    private MedicalServiceParams paths;
}
