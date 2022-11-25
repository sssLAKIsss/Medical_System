package ru.vtb;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SuppressWarnings("ALL")
@Testcontainers
public abstract class TestContainerSetup extends AbstractTest {

    protected static final PostgreSQLContainer container;

    static {
        container = (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
                .withDatabaseName("persons")
                .withUsername("postgres")
                .withPassword("postgres")
                .withReuse(true);
        container.start();
    }

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }
}
