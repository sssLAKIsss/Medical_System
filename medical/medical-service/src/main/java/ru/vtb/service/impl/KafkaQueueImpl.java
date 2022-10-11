package ru.vtb.service.impl;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.vtb.service.IDataQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
@Getter
public class KafkaQueueImpl implements IDataQueue<Long> {
    private final Queue<Long> csvData = new ConcurrentLinkedDeque<>();

    @Override
    public void saveInQueueCsvData(Long csvDataId) {
        csvData.add(csvDataId);
    }

    @Override
    public Long pollCsvData() {
        return csvData.poll();
    }
}
