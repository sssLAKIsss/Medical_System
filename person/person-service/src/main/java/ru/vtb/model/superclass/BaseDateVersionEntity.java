package ru.vtb.model.superclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class BaseDateVersionEntity {

    @Column(name = "visibility", nullable = false)
    private boolean visibility;

    @Column(name = "version", nullable = false)
    @Version
    private int version;

    @Column(name = "last_modified_date",
            nullable = false,
            columnDefinition = "TIMESTAMP",
            updatable = false)
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Column(name = "date_time_creation",
            nullable = false,
            columnDefinition = "TIMESTAMP",
            updatable = false)
    @CreatedDate
    private LocalDateTime dateTimeCreation;
}
