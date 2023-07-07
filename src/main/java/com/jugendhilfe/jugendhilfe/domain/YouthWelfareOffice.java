package com.jugendhilfe.jugendhilfe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="youth_welfare_office")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class YouthWelfareOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="youth_welfare_office_id")
    private Long youthWelfareOfficeId;

    @Column(name="youth_welfare_office_name")
    private String youthWelfareOfficeName;

    @Column(name="youth_welfare_office_street")
    private String youthWelfareOfficeStreet;

    @Column(name="youth_welfare_office_city_code")
    @Size(min=5, max=5)
    private String youthWelfareOfficeCityCode;

    @Column(name="youth_welfare_office_city")
    private String youthWelfareOfficeCity;

    @Column(name="youth_welfare_office_state")
    private String youthWelfareOfficeState;

    @Column(name="youth_welfare_office_country")
    private String youthWelfareOfficeCountry;

    @Column(name="youth_welfare_office_contact_person")
    private String youthWelfareOfficeContactPerson;

    @Column(name="youth_welfare_office_contact_mail")
    private String youthWelfareOfficeContactMail;

    @Column(name="youth_welfare_office_contact_phone")
    private String youthWelfareOfficeContactPhone;

    @Column(name="youth_welfare_office_hint")
    private String youthWelfareOfficeHint;

    @OneToMany(mappedBy = "kindYouthWelfareOffice", cascade = CascadeType.ALL)
    private List<Kind> youthWelfareOfficeKind = new ArrayList<>();

    // nur id-konstruktor für testdaten
    public YouthWelfareOffice(Long youthWelfareOfficeId) {
        this.youthWelfareOfficeId = youthWelfareOfficeId;
    }

    @Override
    public String toString() {
        return "YouthWelfareOffice{" +
                "youthWelfareOfficeId=" + youthWelfareOfficeId +
                ", youthWelfareOfficeName='" + youthWelfareOfficeName + '\'' +
                ", youthWelfareOfficeStreet='" + youthWelfareOfficeStreet + '\'' +
                ", youthWelfareOfficeCityCode='" + youthWelfareOfficeCityCode + '\'' +
                ", youthWelfareOfficeCity='" + youthWelfareOfficeCity + '\'' +
                ", youthWelfareOfficeState='" + youthWelfareOfficeState + '\'' +
                ", youthWelfareOfficeCountry='" + youthWelfareOfficeCountry + '\'' +
                ", youthWelfareOfficeContactPerson='" + youthWelfareOfficeContactPerson + '\'' +
                ", youthWelfareOfficeContactMail='" + youthWelfareOfficeContactMail + '\'' +
                ", youthWelfareOfficeContactPhone='" + youthWelfareOfficeContactPhone + '\'' +
                ", youthWelfareOfficeHint='" + youthWelfareOfficeHint + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YouthWelfareOffice)) return false;
        YouthWelfareOffice that = (YouthWelfareOffice) o;
        return Objects.equals(youthWelfareOfficeId, that.youthWelfareOfficeId) && Objects.equals(youthWelfareOfficeName, that.youthWelfareOfficeName) && Objects.equals(youthWelfareOfficeStreet, that.youthWelfareOfficeStreet) && Objects.equals(youthWelfareOfficeCityCode, that.youthWelfareOfficeCityCode) && Objects.equals(youthWelfareOfficeCity, that.youthWelfareOfficeCity) && Objects.equals(youthWelfareOfficeState, that.youthWelfareOfficeState) && Objects.equals(youthWelfareOfficeCountry, that.youthWelfareOfficeCountry) && Objects.equals(youthWelfareOfficeContactPerson, that.youthWelfareOfficeContactPerson) && Objects.equals(youthWelfareOfficeContactMail, that.youthWelfareOfficeContactMail) && Objects.equals(youthWelfareOfficeContactPhone, that.youthWelfareOfficeContactPhone) && Objects.equals(youthWelfareOfficeHint, that.youthWelfareOfficeHint) && Objects.equals(youthWelfareOfficeKind, that.youthWelfareOfficeKind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(youthWelfareOfficeId, youthWelfareOfficeName, youthWelfareOfficeStreet, youthWelfareOfficeCityCode, youthWelfareOfficeCity, youthWelfareOfficeState, youthWelfareOfficeCountry, youthWelfareOfficeContactPerson, youthWelfareOfficeContactMail, youthWelfareOfficeContactPhone, youthWelfareOfficeHint, youthWelfareOfficeKind);
    }
}
