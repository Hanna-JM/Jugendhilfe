package com.jugendhilfe.jugendhilfe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "wohngruppe")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class Wohngruppe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wohngruppenId")
    private Long wohngruppenId;
    @Column(name = "wohngruppenname", length = 150, nullable = false)
    private String wohngruppenName;
    @NotBlank
    private String strasse;
    @NotBlank
    @Size(min=5, max=5)
    private String plz;
    @NotBlank
    private String ort;
    @Column(name = "anzahlPlaetze", nullable = false)
    private int anzahlPlaetze;
    @Column(name = "tagessatz", nullable = false)
    private int tagessatz;

    @OneToMany(mappedBy = "mitarbeiterDetailWohngruppe", cascade = CascadeType.ALL)
    private List<MitarbeiterDetail> mitarbeiterDetailList = new ArrayList<>();

    // konstruktor für testdaten
    public Wohngruppe(Long wohngruppenId) {
        this.wohngruppenId = wohngruppenId;
    }

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "WohngruppenMitarbeiter",
//            joinColumns = @JoinColumn(name = "wohngruppenId"),
//            inverseJoinColumns = @JoinColumn(name = "mitarbeiterId"))
//    private Set<Mitarbeiter> wgmitarbeiter = new HashSet<>();
//
//    public void mitarbeiterWGHinzufügen (Mitarbeiter mitarbeiter) {wgmitarbeiter.add(mitarbeiter);}

//    public Set<Mitarbeiter> add(Mitarbeiter mitarbeiter,Set<Mitarbeiter> mitarbeiterSet){
//        for (Mitarbeiter m: mitarbeiterSet){
//            if(m.getMitarbeiterId().equals(mitarbeiter.getMitarbeiterId())){
//                return mitarbeiterSet;
//            }
//        }
//        mitarbeiterSet.add(mitarbeiter);
//        return mitarbeiterSet;
//    }

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "WohngruppenKind",
//            joinColumns = @JoinColumn(name = "wohngruppenId"),
//            inverseJoinColumns = @JoinColumn(name = "kindId"))
//    private Set<Kind> wgkind = new HashSet<>();
//
//    public void kindWGHinzufügen (Kind kind) {wgkind.add(kind);}
//
//    public Set<Kind> add(Kind kind,Set<Kind> kindSet){
//        for (Kind k: kindSet){
//            if(k.getKindId().equals(kind.getKindId())){
//                return kindSet;
//            }
//        }
//        kindSet.add(kind);
//        return kindSet;
//    }
    @Override
    public String toString() {
        return "Wohngruppe{" +
                "wohngruppenId=" + wohngruppenId +
                ", wohngruppenname='" + wohngruppenName + '\'' +
                ", strasse='" + strasse + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                ", anzahlPlaetze=" + anzahlPlaetze +
                ", tagessatz=" + tagessatz +
                '}';
    }

    public Long getWohngruppenId() {
        return wohngruppenId;
    }

    public void setWohngruppenId(long wohngruppenId) {
        this.wohngruppenId = wohngruppenId;
    }
}
