package com.jugendhilfe.jugendhilfe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dokumentation_ma")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class DokumentationMA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dokumentationId")
    private Long dokumentationId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dokumentationdatum")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dokumentationdatum;

    @Column(name = "dokumentationtyp")
    private String dokumentationtyp;

    @Column(name = "dokumentationtext")
    private String dokumentationtext;

    @Transient
    public List<String> optionenDokumentationTyp = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "mitarbeiter_id")
    private Mitarbeiter mitarbeiter;
}
