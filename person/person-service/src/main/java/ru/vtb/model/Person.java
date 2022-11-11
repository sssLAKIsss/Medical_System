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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "persons")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Person extends BaseDateVersionEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "persons_id_gen")
    @SequenceGenerator(name = "persons_id_gen", sequenceName = "persons_id_seq", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Фамилия - обязательный атрибут")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Имя - обязательный атрибут")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @OneToMany(cascade = {MERGE, PERSIST, REFRESH})
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    private Set<Document> documents;

    @ManyToMany(fetch = EAGER, cascade = {MERGE, PERSIST, REFRESH})
    @JoinTable(
            name = "persons_addresses",
            joinColumns = @JoinColumn(name = "persons_id"),
            inverseJoinColumns = @JoinColumn(name = "addresses_id")
    )
    private Set<Address> addresses;

    @OneToMany(cascade = {MERGE, PERSIST, REFRESH})
    @JoinColumn(name = "person_id")
    @ToString.Exclude
    private Set<Contact> contacts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return lastName.equals(person.lastName) && firstName.equals(person.firstName) && Objects.equals(patronymic, person.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, patronymic);
    }
}
