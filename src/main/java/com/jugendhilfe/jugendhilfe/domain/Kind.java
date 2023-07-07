package com.jugendhilfe.jugendhilfe.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Entity
@Table(name = "kind")
@Getter
@Setter
public class Kind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kindId")
    private Long kindId;
    @NotBlank(message = "Kind Vorname darf nicht leer sein")
    @Column(name = "kindvorname", nullable = false)
    private String kindVorname;

    @NotBlank(message = "Kind Nachname darf nicht leer sein")
    @Column(name = "kindnachname", nullable = false)
    private String kindNachname;

    @Column(name = "geschlecht", nullable = false)
    private String kindGeschlecht;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "geburtsdatum", nullable = true)
    private LocalDate geburtsdatum;

    @Temporal(TemporalType.DATE)
    //@DateTimeFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "einzug_datum", nullable = false)
    private LocalDate einzug_datum;

    @Temporal(TemporalType.DATE)
    // @DateTimeFormat(pattern = "TT.mm.jjjj")
    // @DateTimeFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "auszug_datum")
    private LocalDate auszug_datum;

    @Column(name = "allergie")
    private String allergie;

    @Column(name = "ernaehrung")
    private String ernaehrung;
    @Column(name = "behinderung")
    private String behinderung;
    @Column(name = "religion")
    private String religion;
    @Column(name = "betreuungsgrund")
    private String betreuungsgrund;

    /**
     * Set does not allow duplicate elements and ensures that each Sorgeberechtiger is unique for
     * a given Kind.
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "kind_sorgeberechtiger",
            joinColumns = @JoinColumn(name = "kindId", referencedColumnName = "kindId"),
            inverseJoinColumns = @JoinColumn(name = "sorgeberechtigerId",
                    referencedColumnName = "sorgeberechtigerId"))
    @JsonManagedReference
    private Set<Sorgeberechtiger> sorgeberechtigerSet = new HashSet<>();

//    @OneToMany(mappedBy = "dokumentationKind", cascade = CascadeType.ALL)
//    private List<Dokumentation> dokumentationen = new ArrayList<>();

    @OneToMany(mappedBy = "kind", cascade = CascadeType.ALL)
    private List<Dokumentation> dokumentationen = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "kind_siblings",
            joinColumns = @JoinColumn(name = "kindId"),
            inverseJoinColumns = @JoinColumn(name = "siblingId"))
    private Set<Kind> siblings = new HashSet<>();
    @OneToMany(mappedBy = "kindDetailKind", cascade = CascadeType.ALL)
    private List<KindDetail> kindDetails = new ArrayList<>();

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "youth_welfare_office_id") //foreign-key
    private YouthWelfareOffice kindYouthWelfareOffice;

    @OneToOne(mappedBy = "kindeinzel")
    private Mitarbeiter mitarbeitereinzel;

    public Kind() {
    }

    public Kind(String kindVorname, String kindNachname, String kindGeschlecht, LocalDate einzug_datum, LocalDate geburtsdatum, String jugendamt) {
        this.kindVorname = kindVorname;
        this.kindNachname = kindNachname;
        this.kindGeschlecht = kindGeschlecht;
        this.einzug_datum = einzug_datum;
//        this.geburtsdatum = LocalDate.of(1900, 1, 1);
        this.geburtsdatum = geburtsdatum;
    }

    // konstruktor für testdaten
    public Kind(Long kindId) {
        this.kindId = kindId;
    }

    public Long getKindId() {
        return kindId;
    }

    public void setKindId(Long kindId) {
        this.kindId = kindId;
    }

    public String getKindVorname() {
        return kindVorname;
    }

    public void setKindVorname(String vorname) {
        this.kindVorname = vorname;
    }

    public String getKindNachname() {
        return kindNachname;
    }

    public void setKindNachname(String nachname) {
        this.kindNachname = nachname;
    }

    public String getKindGeschlecht() {
        return kindGeschlecht;
    }

    public void setKindGeschlecht(String kindGeschlecht) {
        this.kindGeschlecht = kindGeschlecht;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public LocalDate getEinzug_datum() {
        return einzug_datum;
    }

    public void setEinzug_datum(LocalDate einzug_datum) {
        this.einzug_datum = einzug_datum;
    }

    public LocalDate getAuszug_datum() {
        return auszug_datum;
    }

    public void setAuszug_datum(LocalDate auszug_datum) {
        this.auszug_datum = auszug_datum;
    }


    public String getAllergie() {
        return allergie;
    }

    public void setAllergie(String allergie) {
        this.allergie = allergie;
    }

    public String getErnaehrung() {
        return ernaehrung;
    }

    public void setErnaehrung(String ernaehrung) {
        this.ernaehrung = ernaehrung;
    }

    public String getBehinderung() {
        return behinderung;
    }

    public void setBehinderung(String behinderung) {
        this.behinderung = behinderung;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Set<Sorgeberechtiger> getSorgeberechtigerSet() {
        if (sorgeberechtigerSet == null) {
            sorgeberechtigerSet = new HashSet<>();
        }
        return sorgeberechtigerSet;
    }

    public void setSorgeberechtigerSet(Set<Sorgeberechtiger> sorgeberechtigerSet) {
        this.sorgeberechtigerSet = sorgeberechtigerSet;
    }

    public List<Dokumentation> getDokumentationen() {
        return dokumentationen;
    }

    public void setDokumentationen(List<Dokumentation> dokumentationen) {
        this.dokumentationen = dokumentationen;
    }

    public List<KindDetail> getKindDetails() {
        return kindDetails;
    }

    public void setKindDetails(List<KindDetail> kindDetails) {
        this.kindDetails = kindDetails;
    }

    public String getBetreuungsgrund() {
        return betreuungsgrund;
    }

    public void setBetreuungsgrund(String betreuungsgrund) {
        this.betreuungsgrund = betreuungsgrund;
    }

    public YouthWelfareOffice getKindYouthWelfareOffice() {
        return kindYouthWelfareOffice;
    }

    public void setKindYouthWelfareOffice(YouthWelfareOffice kindYouthWelfareOffice) {
        this.kindYouthWelfareOffice = kindYouthWelfareOffice;
    }

    public Mitarbeiter getMitarbeitereinzel() {
        return mitarbeitereinzel;
    }

    public void setMitarbeitereinzel(Mitarbeiter mitarbeitereinzel) {
        this.mitarbeitereinzel = mitarbeitereinzel;
    }

    public Set<Kind> getSiblings() {
        return siblings;
    }

    public void setSiblings(Set<Kind> siblings) {
        this.siblings = siblings;
    }

    public String getAge() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(geburtsdatum, currentDate);

        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        if (years < 1) {
            return months + " months and " + days + " days";
        } else if (years <= 3) {
            return years + " years and " + months + " months";
        } else {
            return years + " years";
        }
    }
//    public int getAge() {
//        LocalDate currentDate = LocalDate.now();
//        Period period = Period.between(geburtsdatum, currentDate);
//        return period.getYears();
//    }

    public boolean isApproaching18Years() {
        LocalDate currentDate = LocalDate.now();
        LocalDate eighteenYearsAgo = currentDate.minusYears(18);
        LocalDate threeMonthsBeforeEighteen = eighteenYearsAgo.minusMonths(3);

        //If the birthdate is after threeMonthsBeforeEighteen,
        // it means the child is still more than three months away from turning 18 and vise versa
        return geburtsdatum.isBefore(threeMonthsBeforeEighteen) || geburtsdatum.equals(threeMonthsBeforeEighteen);
    }


    public int getAgeInMonths() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(geburtsdatum, currentDate);

        int years = period.getYears();
        int months = period.getMonths();

        return years * 12 + months;
    }

    public int getAgeInYears() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(geburtsdatum, currentDate);

        return period.getYears();
    }


    /**
     * Add a Sorgeberechtiger to the set of Sorgeberechtiger for this Kind.
     * It also adds this Kind object to the kinder set of this Sorgeberechtiger object.
     *
     * @param sorgeberechtiger
     */
    public void addSorgeBerechtigter(Sorgeberechtiger sorgeberechtiger) {
        if (!sorgeberechtigerSet.contains(sorgeberechtiger)) {
            this.sorgeberechtigerSet.add(sorgeberechtiger);
            sorgeberechtiger.addKind(this);
//          sorgeberechtiger.getKinderSet().add(this);
        }
    }

    public void addSibling(Kind sibling) {
        if (this != sibling) {
            siblings.add(sibling);
            sibling.getSiblings().add(this);
            //  sibling.getSiblings().addAll(this.getSiblings());

            // Add the sibling to this kind's siblings
            for (Kind existingSibling : siblings) {
                if (existingSibling != sibling) {
                    existingSibling.getSiblings().add(sibling);
                    sibling.getSiblings().add(existingSibling);
                }
            }
        }
    }

    public void removeSibling(Kind sibling) {
        if (sibling != null) {
            siblings.remove(sibling);
            sibling.getSiblings().remove(this);
        }
    }
    public void addMitarbeiterEinzel(Mitarbeiter mitarbeiter) {
        if (mitarbeiter !=null) {
            this.setMitarbeitereinzel(mitarbeiter);
            mitarbeiter.setKindeinzel(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kind)) return false;
        Kind kind = (Kind) o;
        return Objects.equals(kindId, kind.kindId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kindId);
    }


    //    @Override
//    public String toString() {
//        return "Kind{" +
//                "kindId=" + kindId +
//                ", kindVorname='" + kindVorname + '\'' +
//                ", kindNachname='" + kindNachname + '\'' +
//                ", kindGeschlecht='" + kindGeschlecht + '\'' +
//                ", einzug_datum=" + einzug_datum +
//                ", auszug_datum=" + auszug_datum +
//                ", jugendamt='" + jugendamt + '\'' +
//                '}';
//    }
    @Override
    public String toString() {
        return
                kindVorname + " " + kindNachname
                ;
    }

}


