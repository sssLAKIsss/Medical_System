package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vtb.model.csv.CsvFileStructure;
import ru.vtb.service.chain.IBusinessTasksResolver;
import ru.vtb.service.IVaccinationCSVDataService;
import ru.vtb.util.CsvDataParser;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaccinationCSVDataServiceImpl implements IVaccinationCSVDataService {
    private final IBusinessTasksResolver<CsvFileStructure> tasksResolver;

    @Override
    public void uploadDataFromFile(MultipartFile file) {
        if (Objects.isNull(file)) throw new NoSuchElementException();
        CsvDataParser.parseMultipartFileToDataList(CsvFileStructure.class, file)
                .forEach(tasksResolver::executeTasks);
    }
}
