package ru.vtb.service.chain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.model.csv.CsvFileStructure;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvBusinessTasksResolverImpl implements IBusinessTasksResolver<CsvFileStructure> {
    private final List<IBusinessTask<CsvFileStructure>> businessTaskList;

    @Override
    public void executeTasks(CsvFileStructure obj) {
        long count = businessTaskList.stream()
                .filter(task -> OperationType.VALIDATION.equals(task.getOperationName()))
                .filter(task -> !task.execute(obj)).count();
        if (count > 0) return;

        businessTaskList.stream()
                .filter(task -> OperationType.SAVE.equals(task.getOperationName()))
                .forEach(task -> task.execute(obj));
    }
}
