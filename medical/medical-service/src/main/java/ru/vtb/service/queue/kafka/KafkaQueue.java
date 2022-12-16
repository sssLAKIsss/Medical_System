package ru.vtb.service.queue.kafka;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.vtb.service.queue.IDataQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
@Getter
public class KafkaQueue implements IDataQueue<Long> {
    private final Queue<Long> csvData = new ConcurrentLinkedDeque<>();

    @Override
    public void putInQueue(Long csvDataId) {
        csvData.add(csvDataId);
    }

    @Override
    public Long pollData() {
        return csvData.poll();
    }
}
