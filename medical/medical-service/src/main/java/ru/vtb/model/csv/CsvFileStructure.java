package ru.vtb.model.csv;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Builder
public class CsvFileStructure {

    @CsvBindByPosition(position = 0)
    private String firstName;

    @CsvBindByPosition(position = 1)
    private String lastName;

    @CsvBindByPosition(position = 2)
    private String patronymic;

    @CsvBindByPosition(position = 3)
    private String documentNumber;

    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    @CsvBindByPosition(position = 4)
    private LocalDateTime chippingDate;

    @CsvBindByPosition(position = 5)
    private String vaccineTitle;

    @CsvBindByPosition(position = 6)
    private String vaccinationPointNumber;

    @CsvBindByPosition(position = 7)
    private String vaccinationPointTitle;

    @CsvBindByPosition(position = 8)
    private String city;

    @CsvBindByPosition(position = 9)
    private String address;

}
