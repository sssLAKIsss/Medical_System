package ru.vtb.service.chain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vtb.external.PersonsClient;
import ru.vtb.model.csv.CsvFileStructure;
import ru.vtb.service.IDataQueue;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class CsvValidateBusinessTaskImpl implements IBusinessTask<CsvFileStructure> {
    private final IDataQueue<CsvFileStructure> csvFileStructureIDataQueue;
    private final PersonsClient personsClient;

    @Override
    public boolean execute(CsvFileStructure obj) {
        try {
            if (Objects.isNull(obj) || Boolean.FALSE.equals(
                    personsClient.checkByValidPersonData(
                                    obj.getFullName(),
                                    obj.getDocumentNumber(),
                                    true
                            )
                            .getBody())
            ) {
                return false;
            }
            log.info("Check csvData ");
        } catch (Exception e) {
            log.error("Cannot check csvData from person-service");
            csvFileStructureIDataQueue.saveInQueueCsvData(obj);
            return false;
        }
        return true;
    }

    @Override
    public OperationType getOperationName() {
        return OperationType.VALIDATION;
    }
}
