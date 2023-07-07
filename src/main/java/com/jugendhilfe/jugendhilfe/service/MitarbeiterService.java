package com.jugendhilfe.jugendhilfe.service;

import com.jugendhilfe.jugendhilfe.domain.FurtherEducation;
import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.repository.FurtherEducationRepository;
import com.jugendhilfe.jugendhilfe.repository.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MitarbeiterService {

    @Autowired
    private MitarbeiterRepository repoMitarbeiter;

    @Autowired
    private FurtherEducationRepository repoFurtherEducation;

    public void save(Mitarbeiter mitarbeiter) {
        repoMitarbeiter.save(mitarbeiter);
    }

    public Mitarbeiter get(long id) {
        return repoMitarbeiter.findById(id).get();
    }

    public Mitarbeiter getById(long id) {
        return repoMitarbeiter.findById(id).get();
    }

    public void delete(long id) {
        repoMitarbeiter.deleteById(id);
    }


    public List<Mitarbeiter> findAll() {

        return repoMitarbeiter.findAll();
        //return repoMitarbeiter.findAll(Sort.by("nachname").ascending());
    }

    public Mitarbeiter getByMitarbeiterId(Long mitarbeiterId) {
        return repoMitarbeiter.findByMitarbeiterId(mitarbeiterId);
    }

    public List<Mitarbeiter> findByKeyword(String keyword) {
        return repoMitarbeiter.findByKeyword(keyword);
    }

    public List<Mitarbeiter> getAllMitarbeiterWithFurtherEducation() {
        return repoMitarbeiter.findAll();
    }

    public void addOptionAnredeMitarbeiter(Mitarbeiter mitarbeiter){

        if (mitarbeiter.optionenAnredeMitarbeiter.isEmpty()) {
            mitarbeiter.optionenAnredeMitarbeiter.add("Herr");
            mitarbeiter.optionenAnredeMitarbeiter.add("Frau");
            mitarbeiter.optionenAnredeMitarbeiter.add("Divers");
        }
    }

    public Mitarbeiter findById(Long mitarbeiterId) {
        return repoMitarbeiter.findById(mitarbeiterId).orElse(null);
    }



    // TODO vor Auslieferung testdaten entfernen
    // bei Neustart oder zwischendurch daten in db einfügen, vor Auslieferung entfernen
    public void addTestMitarbeiter() {

        Mitarbeiter testMitarbeiter = new Mitarbeiter();
        testMitarbeiter.setMitarbeiterId(1L);
        testMitarbeiter.setAnrede("Herr");
        testMitarbeiter.setVorname("Hans");     // Pflichtfeld
        testMitarbeiter.setNachname("Schmidt"); // Pflichtfeld
        testMitarbeiter.setEmail("test@web.de");
        testMitarbeiter.setStrasse("Hauptstr 2");
        testMitarbeiter.setPlz("12345");
        testMitarbeiter.setOrt("Bremen");
        testMitarbeiter.setMitarbeiterPlaceOfBirth("Huchting");
        testMitarbeiter.setMitarbeiterDayOfBirth(LocalDate.of(1985, 5, 15));  // Pflichtfeld
        testMitarbeiter.setMitarbeiterEducation("Erzieher");
        testMitarbeiter.setMitarbeiterJob("Pfleger");
        testMitarbeiter.setMitarbeiterTelefon("0421-123456");
        testMitarbeiter.setMitarbeiterLanguages("ungarisch");
        testMitarbeiter.setMitarbeiterEintritt(LocalDate.of(2005, 5, 15));
        testMitarbeiter.setMitarbeiterAktivFirma(true); // Pflichtfeld
        repoMitarbeiter.save(testMitarbeiter);


        Mitarbeiter testMitarbeiter2 = new Mitarbeiter();
        testMitarbeiter2.setMitarbeiterId(2L);
        testMitarbeiter2.setAnrede("Frau");
        testMitarbeiter2.setVorname("Erna");    // Pflichtfeld
        testMitarbeiter2.setNachname("Schulz"); // Pflichtfeld
        testMitarbeiter2.setEmail("test2@gmx.de");
        testMitarbeiter2.setStrasse("Nebenstr 112");
        testMitarbeiter2.setPlz("54321");
        testMitarbeiter2.setOrt("Posemuckel");
        testMitarbeiter2.setMitarbeiterPlaceOfBirth("Barsinghausen");
        testMitarbeiter2.setMitarbeiterDayOfBirth(LocalDate.of(1999, 1, 25));   // Pflichtfeld
        testMitarbeiter2.setMitarbeiterEducation("Pflegerin");
        testMitarbeiter2.setMitarbeiterJob("Erzieherin");
        testMitarbeiter2.setMitarbeiterTelefon("555-4321");
        testMitarbeiter2.setMitarbeiterLanguages("englisch, italienisch");
        testMitarbeiter2.setMitarbeiterEintritt(LocalDate.of(2019, 5, 15));
        testMitarbeiter2.setMitarbeiterAktivFirma(true);    // Pflichtfeld
        repoMitarbeiter.save(testMitarbeiter2);

        Mitarbeiter testMitarbeiter3 = new Mitarbeiter();
        testMitarbeiter3.setMitarbeiterId(3L);
        testMitarbeiter3.setAnrede("Divers");
        testMitarbeiter3.setVorname("Otto");        // Pflichtfeld
        testMitarbeiter3.setNachname("Anna");       // Pflichtfeld
        testMitarbeiter3.setEmail("test3@telekom.de");
        testMitarbeiter3.setStrasse("Obernstraße 112");
        testMitarbeiter3.setPlz("85214");
        testMitarbeiter3.setOrt("München");
        testMitarbeiter3.setMitarbeiterPlaceOfBirth("Augsburg");
        testMitarbeiter3.setMitarbeiterDayOfBirth(LocalDate.of(2005, 2, 22));   // Pflichtfeld
        testMitarbeiter3.setMitarbeiterEducation("Student");
        testMitarbeiter3.setMitarbeiterJob("Leiterin");
        testMitarbeiter3.setMitarbeiterTelefon("089-987465");
        testMitarbeiter3.setMitarbeiterLanguages("bayuvarisch");
        testMitarbeiter3.setMitarbeiterEintritt(LocalDate.of(220, 2, 22));
        testMitarbeiter3.setMitarbeiterAktivFirma(true);    // Pflichtfeld
        repoMitarbeiter.save(testMitarbeiter3);
    } // end testdaten


    public int datecheckLoeschenMitarbeiter(Mitarbeiter mitarbeiter) {

        int dateCompareLoeschenMitarbeiter = 0;
        LocalDate today = LocalDate.now();
        if(repoMitarbeiter.findByMitarbeiterId(mitarbeiter.getMitarbeiterId()).getMitarbeiterAustritt() != null) {
            LocalDate maAustrittPlusYear20 = repoMitarbeiter.findByMitarbeiterId(mitarbeiter.getMitarbeiterId()).getMitarbeiterAustritt().plusYears(20).plusDays(1);
            if (maAustrittPlusYear20.isBefore(today)) {
                dateCompareLoeschenMitarbeiter = 1;
            } else {
                dateCompareLoeschenMitarbeiter = 2;
            }
        }
        return dateCompareLoeschenMitarbeiter;
    }


    public void checkMitarbeiterAktivFirma(Mitarbeiter mitarbeiter){

        if (mitarbeiter.getMitarbeiterAustritt() == null ||
                mitarbeiter.getMitarbeiterAustritt().isAfter(LocalDate.now()) ||
                mitarbeiter.getMitarbeiterAustritt().isEqual(LocalDate.now())) {
            mitarbeiter.setMitarbeiterAktivFirma(true);
        } else {
            mitarbeiter.setMitarbeiterAktivFirma(false);
        }

    }

    public void checkAndSaveMitarbeiterAktivFirma(Mitarbeiter mitarbeiter){

        if (mitarbeiter.getMitarbeiterAustritt() == null ||
                mitarbeiter.getMitarbeiterAustritt().isAfter(LocalDate.now()) ||
                mitarbeiter.getMitarbeiterAustritt().isEqual(LocalDate.now())) {
            mitarbeiter.setMitarbeiterAktivFirma(true);
        } else {
            mitarbeiter.setMitarbeiterAktivFirma(false);
        }
        save(mitarbeiter);
    }
}

