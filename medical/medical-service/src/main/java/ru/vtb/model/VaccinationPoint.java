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
@Table(name = "vaccination_points")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class VaccinationPoint extends BaseDateVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaccination_point_id_gen")
    @SequenceGenerator(name = "vaccination_point_id_gen", sequenceName = "vaccination_points_id_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Уникальный номер (сертификат гос. образца) - обязательный атрибут")
    @Column(name = "vaccination_point_number")
    private String vaccinationPointNumber;

    @NotBlank(message = "Наименование - обязательный атрибут")
    @Column(name = "vaccination_point_title")
    private String vaccinationPointTitle;

    @NotBlank(message = "Город - обязательный атрибут")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "Адрес - обязательный атрибут")
    @Column(name = "address")
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaccinationPoint that = (VaccinationPoint) o;
        return vaccinationPointNumber.equals(that.vaccinationPointNumber) && vaccinationPointTitle.equals(that.vaccinationPointTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vaccinationPointNumber, vaccinationPointTitle);
    }
}
