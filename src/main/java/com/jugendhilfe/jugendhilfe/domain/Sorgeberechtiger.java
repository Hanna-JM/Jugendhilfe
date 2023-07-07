package com.jugendhilfe.jugendhilfe.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "sorgeberechtiger")
public class Sorgeberechtiger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sorgeberechtigerId")
    private Long sorgeberechtigerId;

    @Column(name = "anrede")
    private String anrede;
    @NotBlank(message = "Vorname darf nicht leer sein")
    @Column(name = "sorgeberechtiger_vorname", nullable = false)
    private String sorgeberechtigerVorname;
    @NotBlank(message = "Nachname darf nicht leer sein")
    @Column(name = "sorgeberechtiger_nachname", nullable = false)
    private String sorgeberechtigerNachname;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "geburtsdatum", nullable = false)
    private LocalDate geburtsdatum;

    @Column(name = "strasse")
    private String strasse;
    @Column(name = "haus_nummer")
    private String hausNummer;
    @Column(name = "plz")
    private String plz;
    @Column(name = "ort")
    private String ort;
    @Column(name = "email")
    private String email;
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "vewandtschaft")
    private String vewandtschaft;

    @Column(name = "besuchsrecht")
    private String besuchsrecht;

    @Transient
    public List<String> optionenAnredeSorgeberechtigter = new ArrayList<>();


    @ManyToMany(mappedBy = "sorgeberechtigerSet")
    @JsonBackReference
    private Set<Kind> kinderSet = new HashSet<>();

    public Sorgeberechtiger() {
    }

    public Sorgeberechtiger(String sorgeberechtigerVorname, String sorgeberechtigerNachname,LocalDate geburtsdatum) {
        this.sorgeberechtigerVorname = sorgeberechtigerVorname;
        this.sorgeberechtigerNachname = sorgeberechtigerNachname;
        this.geburtsdatum=geburtsdatum;
    }

    public Long getSorgeberechtigerId() {
        return sorgeberechtigerId;
    }

    public void setSorgeberechtigerId(Long sorgeberechtigerId) {
        this.sorgeberechtigerId = sorgeberechtigerId;
    }

    public String getSorgeberechtigerVorname() {
        return sorgeberechtigerVorname;
    }

    public void setSorgeberechtigerVorname(String sorgeberechtigerVorname) {
        this.sorgeberechtigerVorname = sorgeberechtigerVorname;
    }

    public String getSorgeberechtigerNachname() {
        return sorgeberechtigerNachname;
    }

    public void setSorgeberechtigerNachname(String sorgeberechtigerNachname) {
        this.sorgeberechtigerNachname = sorgeberechtigerNachname;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }


    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }
    public String getHausNummer() {
        return hausNummer;
    }

    public void setHausNummer(String hausNummer) {
        this.hausNummer = hausNummer;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getVewandtschaft() {
        return vewandtschaft;
    }

    public void setVewandtschaft(String vewandtschaft) {
        this.vewandtschaft = vewandtschaft;
    }

    public String getBesuchsrecht() {
        return besuchsrecht;
    }

    public void setBesuchsrecht(String besuchsrecht) {
        this.besuchsrecht = besuchsrecht;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public List<String> getOptionenAnredeSorgeberechtigter() {
        return optionenAnredeSorgeberechtigter;
    }

    public void setOptionenAnredeSorgeberechtigter(List<String> optionenAnredeSorgeberechtigter) {
        this.optionenAnredeSorgeberechtigter = optionenAnredeSorgeberechtigter;
    }

    public Set<Kind> getKinderSet() {
        return kinderSet;
    }

    public void setKinderSet(Set<Kind> kinder) {
        this.kinderSet = kinder;
    }

    public void addKind(Kind kind) {
        this.kinderSet.add(kind);
        kind.addSorgeBerechtigter(this);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sorgeberechtiger that)) return false;
        return Objects.equals(sorgeberechtigerId, that.sorgeberechtigerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sorgeberechtigerId);
    }

    @Override
    public String toString() {
        return "Sorgeberechtiger{" +
                "sorgeberechtigerId=" + sorgeberechtigerId +
                ", sorgeberechtigerVorname='" + sorgeberechtigerVorname + '\'' +
                ", sorgeberechtigerNachname='" + sorgeberechtigerNachname + '\'' +
                ", geburtsdatum=" + geburtsdatum +
                '}';
    }


}
