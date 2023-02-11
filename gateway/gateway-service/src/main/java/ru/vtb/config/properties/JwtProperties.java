package ru.vtb.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
@Validated
public class JwtProperties {
    @NotBlank
    private final String secret;
    @NotBlank
    private final String expirationToken;
}
