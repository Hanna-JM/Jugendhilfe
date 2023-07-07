package com.jugendhilfe.jugendhilfe.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "mitarbeiter_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class MitarbeiterDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "mitarbeiterdetailId")
    private Long mitarbeiterDetailId;


//    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "einsatz_beginn")
    private LocalDate einsatzBeginn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "einsatz_ende")
    private LocalDate einsatzEnde;

    @Column (name = "aktiv")
    private Boolean aktiv = true;

    @ManyToOne
    @JoinColumn(name = "mitarbeiter_id")
    private Mitarbeiter mitarbeiterDetailMitarbeiter;

    @ManyToOne
    @JoinColumn(name = "wohngruppen_id")
    private Wohngruppe mitarbeiterDetailWohngruppe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MitarbeiterDetail that = (MitarbeiterDetail) o;
        return Objects.equals(einsatzBeginn, that.einsatzBeginn) &&
                Objects.equals(einsatzEnde, that.einsatzEnde) &&
                Objects.equals(mitarbeiterDetailMitarbeiter, that.mitarbeiterDetailMitarbeiter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(einsatzBeginn, einsatzEnde, mitarbeiterDetailMitarbeiter);
    }
    public boolean isAktiv() {
        return aktiv;
    }

}
