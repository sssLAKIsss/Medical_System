package ru.vtb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig implements OpenApiCustomiser {

    private static final String API_LICENSE = "Лицензировано мной";
    private static final String CONTACT_NAME = "Я";
    private static final String CONTACT_EMAIL = "я@я.я";
    private static final String TITLE = "Справочник клиентов";
    private static final String DESCRIPTION = "Сервис предназначен для чего-то очень важного";
    private static final String DOCS_URL = "когда-нибудь появится";

    @Override
    public void customise(OpenAPI openApi) {
        openApi.info(new Info()
                .title(TITLE)
                .description(DESCRIPTION)
                .license(new License().name(API_LICENSE))
                .contact(new Contact().name(CONTACT_NAME).email(CONTACT_EMAIL)));
    }
}
