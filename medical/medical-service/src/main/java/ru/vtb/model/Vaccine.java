package ru.vtb.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.vtb.model.superclass.BaseDateVersionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "vaccines")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Vaccine extends BaseDateVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaccine_id_gen")
    @SequenceGenerator(name = "vaccine_id_gen", sequenceName = "vaccines_id_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Наименование - обязательный атрибут")
    @Column(name = "vaccine_title")
    private String vaccineTitle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vaccine vaccine = (Vaccine) o;
        return vaccineTitle.equals(vaccine.vaccineTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vaccineTitle);
    }
}
