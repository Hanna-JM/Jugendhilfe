package com.jugendhilfe.jugendhilfe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Entity
@Table(name = "kind_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class KindDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kinddetailId")
    private Long kindDetailId;

//    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "einzug")
    private LocalDate einzug;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "auszug")
    private LocalDate auszug;

    @Column (name = "aktiv")
    private Boolean aktiv = true;

    @ManyToOne
    @JoinColumn(name = "kind_id")
    private Kind kindDetailKind;

    @ManyToOne
    @JoinColumn(name = "wohngruppen_id")
    private Wohngruppe kindDetailWohngruppe;

}
