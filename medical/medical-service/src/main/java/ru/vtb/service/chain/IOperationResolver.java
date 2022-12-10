package ru.vtb.service.chain;

public interface IOperationResolver<T> {
    void executeTasks(T object);
}
