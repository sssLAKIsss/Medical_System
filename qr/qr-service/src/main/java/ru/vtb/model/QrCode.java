package ru.vtb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "qr_codes")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Builder
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qr_codes_id_gen")
    @SequenceGenerator(name = "qr_codes_id_gen", sequenceName = "qr_codes_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "qr_codes", nullable = false)
    private String qrCode;

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @Column(name = "values", nullable = false)
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QrCode qrCode1 = (QrCode) o;
        return Objects.equals(qrCode, qrCode1.qrCode) && passportNumber.equals(qrCode1.passportNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qrCode, passportNumber);
    }
}
