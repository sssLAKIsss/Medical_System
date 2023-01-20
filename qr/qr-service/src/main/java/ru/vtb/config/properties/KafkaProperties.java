package ru.vtb.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@ConfigurationProperties(prefix = "kafka")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
@Validated
public class KafkaProperties {
    @NotEmpty
    private final List<String> topics;
    @NotEmpty
    private final List<String> bootstrapServers;
    @NotBlank
    private final String groupId;

    @Min(1)
    private final int concurrencyValue;

    @NotBlank
    private final String keyDeserializer;
    @NotBlank
    private final String valueDeserializer;
}
