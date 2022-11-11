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
import ru.vtb.model.type.AddressType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Address extends BaseDateVersionEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "addresses_id_gen")
    @SequenceGenerator(name = "addresses_id_gen", sequenceName = "addresses_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AddressType type;

    @NotBlank(message = "Страна в адресе - обязательный атрибут")
    @Column(name = "country")
    private String country;

    @NotBlank(message = "Регион в адресе - обязательный атрибут")
    @Column(name = "region")
    private String region;

    @NotBlank(message = "Город в адресе - обязательный атрибут")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "Улица в адресе - обязательный атрибут")
    @Column(name = "street")
    private String street;

    @Min(value = 1, message = "Номер дома - целое число, большее 1")
    @Column(name = "home")
    private Long home;

    @Min(value = 1, message = "Номер квартиры - целое число, большее 1")
    @Column(name = "flat")
    private Long flat;

    @ManyToMany(mappedBy = "addresses", fetch = FetchType.LAZY, cascade = {MERGE, PERSIST, REFRESH})
    @ToString.Exclude
    private Set<Person> persons;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return type == address.type && country.equals(address.country) && region.equals(address.region) && city.equals(address.city) && street.equals(address.street) && home.equals(address.home) && Objects.equals(flat, address.flat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, country, region, city, street, home, flat);
    }
}
