package ru.vtb;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@AutoConfigureMockMvc
@Slf4j
public abstract class IntegrationAbstractTest extends TestContainerSetup {

    @Autowired
    protected MockMvc mockMvc;

    protected <T> T createObjectFromJson(String filePath, Class<T> classType) {
        try {
            File file = ResourceUtils.getFile(filePath);
            return objectMapper.readValue(file, classType);
        } catch (JsonProcessingException e) {
            log.error("Failed to create object from json!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    protected String getJsonDataByFilePath(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
