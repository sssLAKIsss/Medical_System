package ru.vtb.service.chain;

public interface IBusinessTasksResolver<T> {
    void executeTasks(T object);
}
