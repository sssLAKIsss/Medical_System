package ru.vtb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.vtb.model.superclass.BaseDateVersionEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "patients")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Patient extends BaseDateVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patients_id_gen")
    @SequenceGenerator(name = "patients_id_gen", sequenceName = "patients_id_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Фамилия - обязательный атрибут")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Полное имя - обязательный атрибут")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @NotBlank(message = "Номер документа) - обязательный атрибут")
    @Column(name = "document_number")
    private String documentNumber;

    @OneToMany(cascade = { CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Set<Vaccination> vaccinations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return firstName.equals(patient.firstName) && lastName.equals(patient.lastName) && Objects.equals(patronymic, patient.patronymic) && documentNumber.equals(patient.documentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, patronymic, documentNumber);
    }
}
