package ru.vtb.service;

public interface IDataQueue<T> {
    void saveInQueueCsvData(T queueObject);
    T pollCsvData();
}
