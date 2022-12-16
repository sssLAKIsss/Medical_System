package ru.vtb.service.vaccination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vtb.model.csv.CsvFileStructure;
import ru.vtb.service.chain.IOperationResolver;
import ru.vtb.util.CsvDataParser;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvDataLoader implements ICsvDataLoader {
    private final IOperationResolver<CsvFileStructure> tasksResolver;

    @Override
    public void uploadDataFromFile(MultipartFile file) {
        if (Objects.isNull(file)) throw new NoSuchElementException();
        CsvDataParser.parseMultipartFileToDataList(CsvFileStructure.class, file)
                .forEach(tasksResolver::executeTasks);
    }
}
