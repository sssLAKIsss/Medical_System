package ru.vtb.util;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CsvDataParser {

    public <T> List<T> parseMultipartFileToDataList(Class<T> clazz, MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            return new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .stream()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Произошла ошибка при парсинге файла");
        }
    }
}
