package ru.vtb.service.impl;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.vtb.model.csv.CsvFileStructure;
import ru.vtb.service.IDataQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
@Getter
public class CsvFileStructureQueueImpl implements IDataQueue<CsvFileStructure> {
    private final Queue<CsvFileStructure> csvFileStructureQueue = new ConcurrentLinkedDeque<>();
    @Override
    public void saveInQueueCsvData(CsvFileStructure csvFileStructure) {
        csvFileStructureQueue.add(csvFileStructure);
    }

    @Override
    public CsvFileStructure pollCsvData() {
        return csvFileStructureQueue.poll();
    }
}
