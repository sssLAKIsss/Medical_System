package ru.vtb.service.queue;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.vtb.model.csv.CsvFileStructure;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
@Getter
public class CsvDataQueue implements IDataQueue<CsvFileStructure> {
    private final Queue<CsvFileStructure> csvFileStructureQueue = new ConcurrentLinkedDeque<>();
    @Override
    public void putInQueue(CsvFileStructure csvFileStructure) {
        csvFileStructureQueue.add(csvFileStructure);
    }

    @Override
    public CsvFileStructure pollData() {
        return csvFileStructureQueue.poll();
    }
}
