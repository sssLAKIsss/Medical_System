package ru.vtb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Address extends BaseDateVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addresses_id_gen")
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

    @ManyToMany(mappedBy = "addresses", fetch = FetchType.LAZY)
    private Set<Person> persons;
}
