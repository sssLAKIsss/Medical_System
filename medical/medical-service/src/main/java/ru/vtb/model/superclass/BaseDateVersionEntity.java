package ru.vtb.model.superclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseDateVersionEntity {

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "version", nullable = false)
    @Version
    private int version;

    @Column(name = "last_modified_date",
            nullable = false,
            columnDefinition = "TIMESTAMP",
            updatable = false)
    @LastModifiedDate
    private Instant lastModifiedDate;

    @Column(name = "date_time_creation",
            nullable = false,
            columnDefinition = "TIMESTAMP",
            updatable = false)
    @CreatedDate
    private Instant dateTimeCreation;
}
