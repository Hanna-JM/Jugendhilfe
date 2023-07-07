package com.jugendhilfe.jugendhilfe.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "further_education")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class FurtherEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "further_education_id")
    private Long furtherEducationId;

    @Column(name = "further_education_name")
    private String furtherEducationName;

    @Column(name = "further_education_cycle")
    private String furtherEducationCycle;

    @Column(name = "further_education_type")
    private String furtherEducationType;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "further_education_date_last_exam")
    private LocalDate furtherEducationDateLastExam;

    @ManyToOne
    @JoinColumn(name = "mitarbeiter_id")
    private Mitarbeiter furtherEducationMitarbeiter;

    @Transient
    private List<String> furtherEducationCycleOptions = new ArrayList<>();

    @Transient
    private List<String> furtherEducationTypeOptions = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FurtherEducation)) return false;
        FurtherEducation that = (FurtherEducation) o;
        return Objects.equals(furtherEducationId, that.furtherEducationId) && Objects.equals(furtherEducationName, that.furtherEducationName) && Objects.equals(furtherEducationCycle, that.furtherEducationCycle) && Objects.equals(furtherEducationType, that.furtherEducationType) && Objects.equals(furtherEducationDateLastExam, that.furtherEducationDateLastExam) && Objects.equals(furtherEducationMitarbeiter, that.furtherEducationMitarbeiter) && Objects.equals(furtherEducationCycleOptions, that.furtherEducationCycleOptions) && Objects.equals(furtherEducationTypeOptions, that.furtherEducationTypeOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(furtherEducationId, furtherEducationName, furtherEducationCycle, furtherEducationType, furtherEducationDateLastExam, furtherEducationMitarbeiter, furtherEducationCycleOptions, furtherEducationTypeOptions);
    }
}
