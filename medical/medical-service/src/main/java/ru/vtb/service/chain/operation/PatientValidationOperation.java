package ru.vtb.service.chain.operation;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.vtb.external.PersonsClient;
import ru.vtb.model.csv.CsvFileStructure;
import ru.vtb.service.queue.IDataQueue;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class PatientValidationOperation implements IOperation<CsvFileStructure> {
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
                log.info("Пользователь {} {} не прошел проверку в справочнике", obj.getFirstName(), obj.getLastName());
                return false;
            }
            log.info("Check csvData ");
        } catch (Exception e) {
            if (e instanceof FeignException && ((FeignException) e).status() == HttpStatus.NOT_FOUND.value()) {
                log.info(
                        "Person service return 404 with patient passportNumber {}. \nExternal exception {}",
                        obj.getDocumentNumber(),
                        e.getMessage());
                return false;
            }
            log.error(
                    "External exception with message: {}. Cannot check csvData from person-service",
                    e.getMessage());
            csvFileStructureIDataQueue.putInQueue(obj);
            return false;
        }
        return true;
    }

    @Override
    public OperationType getOperationName() {
        return OperationType.VALIDATION;
    }
}
