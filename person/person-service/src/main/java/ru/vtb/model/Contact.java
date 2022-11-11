package ru.vtb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.vtb.model.superclass.BaseDateVersionEntity;
import ru.vtb.model.type.ContactType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Contact extends BaseDateVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacts_id_gen")
    @SequenceGenerator(name = "contacts_id_gen", sequenceName = "contacts_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ContactType type;

    @NotBlank(message = "Номер телефона клиента - обязательный атрибут")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "person_id")
    private Long personId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return phoneNumber.equals(contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }
}
