package ru.vtb.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vtb.model.csv.CsvFileStructure;
import ru.vtb.service.chain.IOperationResolver;
import ru.vtb.service.queue.IDataQueue;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvDataRetryer implements IScheduler {
    private final IOperationResolver<CsvFileStructure> businessTasksResolver;
    private final IDataQueue<CsvFileStructure> csvFileStructureIDataQueue;

    private static final long DELAY = 1000L;

    @Override
    @Scheduled(fixedDelay = DELAY)
    public void scheduleTask() {
        CsvFileStructure c = csvFileStructureIDataQueue.pollData();
        if (Objects.isNull(c)) return;
        log.info("Try to validate vaccination with name = " + String.join(
                " ",
                c.getLastName(),
                c.getFirstName(),
                c.getPatronymic()));
        businessTasksResolver.executeTasks(c);
    }
}
