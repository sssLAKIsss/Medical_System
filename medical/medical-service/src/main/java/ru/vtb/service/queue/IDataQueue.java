package ru.vtb.service.queue;

public interface IDataQueue<T> {
    void putInQueue(T queueObject);
    T pollData();
}
