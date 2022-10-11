package ru.vtb.service.impl.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vtb.model.csv.CsvFileStructure;
import ru.vtb.service.chain.IBusinessTasksResolver;
import ru.vtb.service.IDataQueue;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvFileStructureSchedulerImpl implements IScheduler {
    private final IBusinessTasksResolver<CsvFileStructure> businessTasksResolver;
    private final IDataQueue<CsvFileStructure> csvFileStructureIDataQueue;

    private static final long DELAY = 1000L;

    @Override
    @Scheduled(fixedDelay = DELAY)
    public void scheduleTask() {
        CsvFileStructure c = csvFileStructureIDataQueue.pollCsvData();
        if (Objects.isNull(c)) return;
        log.info("Try to validate vaccination with name = " + c.getFullName());
        businessTasksResolver.executeTasks(c);
    }
}
