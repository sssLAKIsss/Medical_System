package ru.vtb.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CsvDataParser {

    public <T> List<T> parseMultipartFileToDataList(Class<T> clazz, MultipartFile file)  {
        List<T> dataList = new ArrayList<>();
//        CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
        try(Reader reader = new InputStreamReader(file.getInputStream())) {
            CsvToBean<T> toCsvToBean = new CsvToBeanBuilder(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (T dataObject : toCsvToBean) {
                dataList.add(dataObject);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }


}
