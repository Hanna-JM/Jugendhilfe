package com.jugendhilfe.jugendhilfe.service;

import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.YouthWelfareOffice;
import com.jugendhilfe.jugendhilfe.repository.YouthWelfareOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class YouthWelfareOfficeService {

    @Autowired
    YouthWelfareOfficeRepository youthWelfareOfficeRepository;

    public void save(YouthWelfareOffice youthWelfareOffice){
        youthWelfareOfficeRepository.save(youthWelfareOffice);
    }

    public void delete(long youthWelfareOfficeId){
        youthWelfareOfficeRepository.deleteById(youthWelfareOfficeId);
    }

    public List<YouthWelfareOffice> findAll(){
        return youthWelfareOfficeRepository.findAll();
    }

    public YouthWelfareOffice getByYouthWelfareOfficeId(Long youthWelfareOfficeId){
        return youthWelfareOfficeRepository.findByYouthWelfareOfficeId(youthWelfareOfficeId);
    }

    public List<YouthWelfareOffice> findByKeyword(String keyword){
        return youthWelfareOfficeRepository.findByKeyword(keyword);
    }


    // TODO vor Auslieferung entfernen
    // bei neustart oder zwischendurch, datenbank mit testdaten füllen, vor Auslieferung entfernen
    public void addTestYWO() {

        Kind kind1 = new Kind("Steffi","Oertli","weiblich", LocalDate.of(2023,4,12), LocalDate.of(2013,5,30),"Bremen Mitte");
        List<Kind> youthWelfareOfficeKindList1 = new ArrayList<>();
        youthWelfareOfficeKindList1.add(kind1);

        Kind kind2 = new Kind("Stefan","Weltlig","männlich",LocalDate.of(2022,12,23), LocalDate.of(2018,12,31),"Hamburg Blankeneese");
        List<Kind> youthWelfareOfficeKindList2 = new ArrayList<>();
        youthWelfareOfficeKindList1.add(kind2);

        Kind kind3 = new Kind("Anne","Theke","divers",LocalDate.of(2022,12,23),LocalDate.of(2015,2,26), "Hamburg Blankeneese");
        List<Kind> youthWelfareOfficeKindList3 = new ArrayList<>();
        youthWelfareOfficeKindList1.add(kind3);

        YouthWelfareOffice youthWelfareOffice1 = new YouthWelfareOffice();
        youthWelfareOffice1.setYouthWelfareOfficeId(1L);
        youthWelfareOffice1.setYouthWelfareOfficeName("Bremen Mitte");
        youthWelfareOffice1.setYouthWelfareOfficeStreet("hauptstr 2");
        youthWelfareOffice1.setYouthWelfareOfficeCityCode("28215");
        youthWelfareOffice1.setYouthWelfareOfficeCity("Bremen");
        youthWelfareOffice1.setYouthWelfareOfficeState("Bremen");
        youthWelfareOffice1.setYouthWelfareOfficeCountry("Deu");
        youthWelfareOffice1.setYouthWelfareOfficeContactPerson("Herr meyer");
        youthWelfareOffice1.setYouthWelfareOfficeContactPhone("0421-258535");
        youthWelfareOffice1.setYouthWelfareOfficeContactMail("meyer@jughb.de");
        youthWelfareOffice1.setYouthWelfareOfficeHint("bis 17.00 Uhr");
  //      youthWelfareOffice1.setYouthWelfareOfficeKind(youthWelfareOfficeKindList1);
        youthWelfareOfficeRepository.save(youthWelfareOffice1);


        YouthWelfareOffice youthWelfareOffice2 = new YouthWelfareOffice();
        youthWelfareOffice2.setYouthWelfareOfficeId(2L);
        youthWelfareOffice2.setYouthWelfareOfficeName("Hamburg Blankeneese");
        youthWelfareOffice2.setYouthWelfareOfficeStreet("Nebenstr 2");
        youthWelfareOffice2.setYouthWelfareOfficeCityCode("21452");
        youthWelfareOffice2.setYouthWelfareOfficeCity("Hamburg");
        youthWelfareOffice2.setYouthWelfareOfficeState("Hamburg");
        youthWelfareOffice2.setYouthWelfareOfficeCountry("Deu");
        youthWelfareOffice2.setYouthWelfareOfficeContactPerson("Frau Kuse");
        youthWelfareOffice2.setYouthWelfareOfficeContactPhone("040-787545");
        youthWelfareOffice2.setYouthWelfareOfficeContactMail("kuse@jughh.de");
        youthWelfareOffice2.setYouthWelfareOfficeHint("ab 17.00 Uhr");
  //      youthWelfareOffice1.setYouthWelfareOfficeKind(youthWelfareOfficeKindList2);
        youthWelfareOfficeRepository.save(youthWelfareOffice2);

        YouthWelfareOffice youthWelfareOffice3 = new YouthWelfareOffice();
        youthWelfareOffice3.setYouthWelfareOfficeId(3L);
        youthWelfareOffice3.setYouthWelfareOfficeName("Hamburg Steilshoop");
        youthWelfareOffice3.setYouthWelfareOfficeStreet("Auf dem Berge 11");
        youthWelfareOffice3.setYouthWelfareOfficeCityCode("21258");
        youthWelfareOffice3.setYouthWelfareOfficeCity("Hamburg");
        youthWelfareOffice3.setYouthWelfareOfficeState("Hamburg");
        youthWelfareOffice3.setYouthWelfareOfficeCountry("Deu");
        youthWelfareOffice3.setYouthWelfareOfficeContactPerson("Herr Starke");
        youthWelfareOffice3.setYouthWelfareOfficeContactPhone("040-54754");
        youthWelfareOffice3.setYouthWelfareOfficeContactMail("starke@jughh.de");
        youthWelfareOffice3.setYouthWelfareOfficeHint("24/7 is not enough");
//        youthWelfareOffice1.setYouthWelfareOfficeKind(youthWelfareOfficeKindList3);
        youthWelfareOfficeRepository.save(youthWelfareOffice3);

    } // end testdaten


}
