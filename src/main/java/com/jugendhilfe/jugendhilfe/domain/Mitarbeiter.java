package com.jugendhilfe.jugendhilfe.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "mitarbeiter")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mitarbeiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mitarbeiterId")
    private Long mitarbeiterId;
    @Column(name = "anrede", length = 150)
    private String anrede;
    @Column(name = "vorname", length = 250)
    private String vorname;

    @Column(name = "nachname")
    private String nachname;
    @Column(name = "email")
    private String email;
    @Column(name = "strasse")
    private String strasse;
    @Column(name = "plz")
    private String plz;
    @Column(name = "ort")
    private String ort;


    @Column(name = "mitarbeiter_place_of_birth")
    private String mitarbeiterPlaceOfBirth;

    //@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "mitarbeiter_day_of_birth")
    private LocalDate mitarbeiterDayOfBirth;

    @Column(name = "mitarbeiter_education")
    private String mitarbeiterEducation;

    @Column(name="mitarbeiter_job")
    private String mitarbeiterJob;


    @Column(name = "mitarbeiter_telefon")
    private String mitarbeiterTelefon;

    @Column(name = "mitarbeiter_languages")
    private String mitarbeiterLanguages;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "mitarbeiter_eintritt")
    private LocalDate mitarbeiterEintritt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "mitarbeiter_austritt")
    private LocalDate mitarbeiterAustritt;

    @Column(name= "mitarbeiter_aktiv_firma")
    private Boolean mitarbeiterAktivFirma = true;


    @Transient
    public List<String> optionenAnredeMitarbeiter = new ArrayList<>();

    @OneToMany(mappedBy = "furtherEducationMitarbeiter", cascade = CascadeType.ALL)
    private List<FurtherEducation> mitarbeiterFurtherEducations = new ArrayList<>();

    @OneToMany(mappedBy = "mitarbeiterDetailMitarbeiter", cascade = CascadeType.ALL)
    private List<MitarbeiterDetail> mitarbeitermitarbeiterDetails = new ArrayList<>();

    @OneToMany(mappedBy = "mitarbeiter", cascade = CascadeType.ALL)
    private List<DokumentationMA> dokumentationenma = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "kind_id")
    private Kind kindeinzel;


    // für testdaten
    public Mitarbeiter(Long mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
    }


    public void setAnrede(String anrede) {
        this.anrede = anrede.trim();
    }

    public void setVorname(String vorname) {
        this.vorname = vorname.trim();
    }

    public void setNachname(String nachname) {
        this.nachname = nachname.trim();
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse.trim();
    }

    public void setPlz(String plz) {
        this.plz = plz.trim();
    }

    public void setOrt(String ort) {
        this.ort = ort.trim();
    }

    public void setMitarbeiterPlaceOfBirth(String mitarbeiterPlaceOfBirth) {
        this.mitarbeiterPlaceOfBirth = mitarbeiterPlaceOfBirth.trim();
    }

    public void setMitarbeiterDayOfBirth(LocalDate mitarbeiterDayOfBirth) {
        this.mitarbeiterDayOfBirth = mitarbeiterDayOfBirth;
    }

    public void setMitarbeiterEducation(String mitarbeiterEducation) {
        this.mitarbeiterEducation = mitarbeiterEducation.trim();
    }

    public void setMitarbeiterJob(String mitarbeiterJob) {
        this.mitarbeiterJob = mitarbeiterJob.trim();
    }

    public void setMitarbeiterTelefon(String mitarbeiterTelefon) {
        this.mitarbeiterTelefon = mitarbeiterTelefon.trim();
    }

    public void setMitarbeiterLanguages(String mitarbeiterLanguages) {
        this.mitarbeiterLanguages = mitarbeiterLanguages.trim();
    }

    public void setMitarbeiterEintritt(LocalDate mitarbeiterEintritt) {
        this.mitarbeiterEintritt = mitarbeiterEintritt;
    }

    public void setMitarbeiterAustritt(LocalDate mitarbeiterAustritt) {
        this.mitarbeiterAustritt = mitarbeiterAustritt;
    }

    @Override
    public String toString() {
        return
                "" + mitarbeiterId +
                ", " + vorname  +
                ", " + nachname ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mitarbeiter that = (Mitarbeiter) o;
        return anrede == that.anrede && Objects.equals(mitarbeiterId, that.mitarbeiterId) && vorname.equals(that.vorname) && nachname.equals(that.nachname) && email.equals(that.email) && strasse.equals(that.strasse) && plz.equals(that.plz) && ort.equals(that.ort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vorname, nachname, email, strasse, plz, ort);
    }

    //    public void setKind(Kind kind) {
//    }
//
//    public Mitarbeiter getMitarbeiter() {
//    }
//
//    public Kind getKind() {
//    }

    public void addKindEinzel(Kind kind) {
        if (kind !=null) {
            this.setKindeinzel(kind);
            kind.setMitarbeitereinzel(this);
        }
    }
}