package com.jugendhilfe.jugendhilfe.service;

import com.jugendhilfe.jugendhilfe.domain.FurtherEducation;
import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.repository.FurtherEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FurtherEducationService {


    @Autowired
    private FurtherEducationRepository furtherEducationRepository;

    public void save(FurtherEducation furtherEducation) {
        furtherEducationRepository.save(furtherEducation);
    }

    public FurtherEducation get(long id) {
        return furtherEducationRepository.findById(id).get();
    }

    public void delete(long id) {
        furtherEducationRepository.deleteById(id);
    }


    public List<FurtherEducation> findAll() {

        return furtherEducationRepository.findAll();
        //return repoMitarbeiter.findAll(Sort.by("furtherEducationName").ascending());
    }

    public FurtherEducation getByFurtherEducationId(Long furtherEducationId) {
        return furtherEducationRepository.findByFurtherEducationId(furtherEducationId);
    }

    public List<FurtherEducation> findByKeyword(String keyword) {
        return furtherEducationRepository.findByKeyword(keyword);
    }


    public void addFurtherEducationCycleOptions(FurtherEducation furtherEducation) {

        if (furtherEducation.getFurtherEducationCycleOptions().isEmpty()) {
//            furtherEducation.furtherEducationCycleOptions.add("jährlich");
//            furtherEducation.furtherEducationCycleOptions.add("halbjährlich");
//            furtherEducation.furtherEducationCycleOptions.add("vierteljährlich");
//            furtherEducation.furtherEducationCycleOptions.add("monatlich");
//            furtherEducation.furtherEducationCycleOptions.add("wöchentlich");
//            furtherEducation.furtherEducationCycleOptions.add("täglich");

            furtherEducation.getFurtherEducationCycleOptions().add("jaehrlich");
            furtherEducation.getFurtherEducationCycleOptions().add("halbjaehrlich");
            furtherEducation.getFurtherEducationCycleOptions().add("vierteljaehrlich");
            furtherEducation.getFurtherEducationCycleOptions().add("monatlich");
            furtherEducation.getFurtherEducationCycleOptions().add("woechentlich");
            furtherEducation.getFurtherEducationCycleOptions().add("taeglich");
        }

    }


    public void addFurtherEducationTypeOptions(FurtherEducation furtherEducation) {

        if (furtherEducation.getFurtherEducationTypeOptions().isEmpty()) {
            furtherEducation.getFurtherEducationTypeOptions().add("freiwillig");
            furtherEducation.getFurtherEducationTypeOptions().add("pflicht");
        }
    }


    public  int datecheck(FurtherEducation furtherEducation){

    int dateCompare = 0;

    LocalDate feLastExam = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam();
    LocalDate today = LocalDate.now();

    LocalDate todayPlusMonth = today.plusDays(30);
    LocalDate fePlusYear = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam().plusYears(1);
    LocalDate fePlusMonth11 = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam().plusMonths(11);
        if (furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationCycle().equalsIgnoreCase( "jährlich") || furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationCycle().equalsIgnoreCase( "jaehrlich")) {
        // umstellung auf jahr vergangenheit/monat im voraus
        if (fePlusMonth11.isBefore(today)) {
            dateCompare = 1;    // zw 11 u 12 monaten vor ablauf, orange
        }
        if (fePlusYear.isBefore(today)) {
            dateCompare = 2;    // nach 12 monaten abgelaufen, darkred
        }
        if (fePlusYear.isEqual(today)) {
            dateCompare = 3;    // heute abgelaufen, red
        }
    }

    LocalDate fePlusMonth6 = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam().plusMonths(6);
    LocalDate fePlusMonth5 = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam().plusMonths(5);
        if (furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationCycle().equalsIgnoreCase( "halbjährlich") || furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationCycle().equalsIgnoreCase( "halbjaehrlich")) {
        // umstellung auf jahr vergangenheit/monat im voraus
        if (fePlusMonth5.isBefore(today)) {
            dateCompare = 1;    // zw 5 u 6 monaten vor ablauf, orange
        }
        if (fePlusMonth6.isBefore(today)) {
            dateCompare = 2;    // nach 12 monaten abgelaufen, darkred
        }
        if (fePlusMonth6.isEqual(today)) {
            dateCompare = 3;    // heute abgelaufen, red
        }
    }


    LocalDate fePlusMonth3 = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam().plusMonths(3);
    LocalDate fePlusMonth2 = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam().plusMonths(2);
        if (furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationCycle().equalsIgnoreCase( "vierteljährlich") || furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationCycle().equalsIgnoreCase( "vierteljaehrlich")) {
        // umstellung auf jahr vergangenheit/monat im voraus
        if (fePlusMonth2.isBefore(today)) {
            dateCompare = 1;    // zw 2 u 3 monaten vor ablauf, orange
        }
        if (fePlusMonth3.isBefore(today)) {
            dateCompare = 2;    // nach 3 monaten abgelaufen, darkred
        }
        if (fePlusMonth3.isEqual(today)) {
            dateCompare = 3;    // heute abgelaufen, red
        }
    }

    LocalDate fePlusMonth = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam().plusDays(30);
    LocalDate fePlus21Days = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam().plusDays(21);
        if (furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationCycle().equalsIgnoreCase( "monatlich")) {
        // umstellung auf jahr vergangenheit/monat im voraus
        if (fePlus21Days.isBefore(today)) {
            dateCompare = 1;    // zw 3 u 4 wochen vor ablauf, orange
        }
        if (fePlusMonth.isBefore(today)) {
            dateCompare = 2;    // nach 4 wochen abgelaufen, darkred
        }
        if (fePlusMonth.isEqual(today)) {
            dateCompare = 3;    // heute abgelaufen, red
        }
    }

    LocalDate fePlusWeek = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam().plusDays(7);
    LocalDate fePlus5Days = furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationDateLastExam().plusDays(5);
        if (furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationCycle().equalsIgnoreCase( "wöchentlich") || furtherEducationRepository.findByFurtherEducationId(furtherEducation.getFurtherEducationId()).getFurtherEducationCycle().equalsIgnoreCase( "woechentlich")) {
        // umstellung auf jahr vergangenheit/monat im voraus
        if (fePlus5Days.isBefore(today)) {
            dateCompare = 1;    //  6 tage vor ablauf, orange
        }
        if (fePlusWeek.isBefore(today)) {
            dateCompare = 2;    // nach 7 tagen abgelaufen, darkred
        }
        if (fePlusWeek.isEqual(today)) {
            dateCompare = 3;    // heute abgelaufen, red
        }
    }

        return dateCompare;
} // end datecheck


    // TODO vor Auslieferung entfernen
    // bei neustart oder zwischendurch, datenbank mit testdaten füllen, vor Auslieferung entfernen
    public void addTestFurtherEducation() {

        FurtherEducation testFurtherEducation1 = new FurtherEducation();
        testFurtherEducation1.setFurtherEducationId(1L);
        testFurtherEducation1.setFurtherEducationName("erste Hilfe");
        testFurtherEducation1.setFurtherEducationCycle("jaehrlich");
        testFurtherEducation1.setFurtherEducationType("pflicht");
        testFurtherEducation1.setFurtherEducationDateLastExam(LocalDate.of(2022, 12, 12));
        testFurtherEducation1.setFurtherEducationMitarbeiter(new Mitarbeiter(1L));
        furtherEducationRepository.save(testFurtherEducation1);

        FurtherEducation testFurtherEducation2 = new FurtherEducation();
        testFurtherEducation2.setFurtherEducationId(2L);
        testFurtherEducation2.setFurtherEducationName("Awareness DSGVO");
        testFurtherEducation2.setFurtherEducationCycle("monatlich");
        testFurtherEducation2.setFurtherEducationType("freiwillig");
        testFurtherEducation2.setFurtherEducationDateLastExam(LocalDate.of(2023, 3, 5));
        testFurtherEducation2.setFurtherEducationMitarbeiter(new Mitarbeiter(2L));
        furtherEducationRepository.save(testFurtherEducation2);

        FurtherEducation testFurtherEducation3 = new FurtherEducation();
        testFurtherEducation3.setFurtherEducationId(3L);
        testFurtherEducation3.setFurtherEducationName("Supervision");
        testFurtherEducation3.setFurtherEducationCycle("woechentlich");
        testFurtherEducation3.setFurtherEducationType("pflicht");
        testFurtherEducation3.setFurtherEducationDateLastExam(LocalDate.of(2023, 4, 15));
        testFurtherEducation3.setFurtherEducationMitarbeiter(new Mitarbeiter(3L));
        furtherEducationRepository.save(testFurtherEducation3);
    } // end testdaten

}
