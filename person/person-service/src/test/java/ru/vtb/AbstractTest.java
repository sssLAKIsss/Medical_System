package ru.vtb;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = PersonService.class)
public abstract class AbstractTest {
    @Autowired
    protected ObjectMapper objectMapper;

    @SneakyThrows
    protected String asJsonFromObject(Object o) {
        return objectMapper.writeValueAsString(o);
    }
}
