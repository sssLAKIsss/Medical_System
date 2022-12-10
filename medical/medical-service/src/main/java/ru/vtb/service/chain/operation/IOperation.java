package ru.vtb.service.chain.operation;

public interface IOperation<T> {
    boolean execute(T obj);
    OperationType getOperationName();
}
