package ru.vtb.service.chain;

public interface IBusinessTask<T> {
    boolean execute(T obj);
    OperationType getOperationName();
}
