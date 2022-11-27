package ru.vtb.service.chain;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.vtb.external.PersonsClient;
import ru.vtb.model.csv.CsvFileStructure;
import ru.vtb.service.IDataQueue;

import static java.util.Objects.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class CsvValidateBusinessTaskImpl implements IBusinessTask<CsvFileStructure> {
    private final IDataQueue<CsvFileStructure> csvFileStructureIDataQueue;
    private final PersonsClient personsClient;

    @Override
    public boolean execute(CsvFileStructure obj) {
        try {
            if (isNull(obj) || Boolean.FALSE.equals(
                    personsClient.checkByValidPersonData(
                                    obj.getFirstName(),
                                    obj.getLastName(),
                                    obj.getDocumentNumber(),
                                    obj.getPatronymic(),
                                    Boolean.TRUE)
                            .getBody())
            ) {
                return false;
            }
            log.info("Check csvData ");
        } catch (Exception e) {
            if (e instanceof FeignException && ((FeignException) e).status() == HttpStatus.NOT_FOUND.value()) {
                log.error("External exception {} with message {}", e, e.getMessage());
                return false;
            }
            log.error("External exception: {}, with message: {}. Cannot check csvData from person-service",
                    e,
                    e.getMessage());
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
