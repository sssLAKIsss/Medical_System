package ru.vtb.service.chain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.model.csv.CsvFileStructure;
import ru.vtb.service.chain.operation.IOperation;
import ru.vtb.service.chain.operation.OperationType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationResolver implements IOperationResolver<CsvFileStructure> {
    private final List<IOperation<CsvFileStructure>> businessTaskList;

    @Override
    public void executeTasks(CsvFileStructure obj) {
        long count = businessTaskList.stream()
                .filter(task -> OperationType.VALIDATION.equals(task.getOperationName()))
                .filter(task -> !task.execute(obj))
                .count();
        if (count > 0) return;

        businessTaskList.stream()
                .filter(task -> OperationType.SAVE.equals(task.getOperationName()))
                .forEach(task -> task.execute(obj));
    }
}
