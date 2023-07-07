package com.jugendhilfe.jugendhilfe.service;

import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.domain.Sorgeberechtiger;
import com.jugendhilfe.jugendhilfe.repository.KindRepository;
import com.jugendhilfe.jugendhilfe.repository.SorgeberechtigerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SorgeberechtigerService {

    @Autowired
    private SorgeberechtigerRepository sorgeberechtigerRepository;


    public void save(Sorgeberechtiger sorgeberechtiger) {
        sorgeberechtigerRepository.save(sorgeberechtiger);
    }

    public Sorgeberechtiger get(Long id) {
        Optional<Sorgeberechtiger> optionalSorgeberechtiger = sorgeberechtigerRepository.findById(id);
        //        } else {
        //            throw new NotFoundException("Sorgeberechtiger with id " + sorgeberechtigerId + " not found");
        //        }
        return optionalSorgeberechtiger.orElseGet(optionalSorgeberechtiger::get);
    }

    public List<Sorgeberechtiger> findByKeyword(String keyword) {
        return sorgeberechtigerRepository.findByKeyword(keyword);
    }

    public List<Sorgeberechtiger> listAll() {
        return sorgeberechtigerRepository.findAll();
    }

    /**
     * check if the Sorgeberechtiger object exists in the database before deleting it.
     * @param id
     */
    public void delete(long id) {
        Optional<Sorgeberechtiger> sorgeberechtigerOptional = sorgeberechtigerRepository.findById(id);
        if (sorgeberechtigerOptional.isPresent()) {
            sorgeberechtigerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Sorgeberechtiger with ID " + id + " not found.");
        }
    }

    public void addOptionAnredeSorgeberechtigter(Sorgeberechtiger sorgeberechtiger){

        if (sorgeberechtiger.optionenAnredeSorgeberechtigter.isEmpty()) {
            sorgeberechtiger.optionenAnredeSorgeberechtigter.add("Herr");
            sorgeberechtiger.optionenAnredeSorgeberechtigter.add("Frau");
            sorgeberechtiger.optionenAnredeSorgeberechtigter.add("Divers");
        }
    }

    // TODO vor Auslieferung entfernen
    // bei neustart oder zwischendurch, datenbank mit testdaten füllen, vor Auslieferung entfernen
    public void addTestSorgeberechtigter() {

        Sorgeberechtiger testSorgeberechtiger1 = new Sorgeberechtiger();
        testSorgeberechtiger1.setSorgeberechtigerId(1l);
        testSorgeberechtiger1.setSorgeberechtigerVorname("Willi");
        testSorgeberechtiger1.setSorgeberechtigerNachname("Winzig");

        testSorgeberechtiger1.setGeburtsdatum(LocalDate.of(1999, 05, 24));
        sorgeberechtigerRepository.save(testSorgeberechtiger1);

        Sorgeberechtiger testSorgeberechtiger2 = new Sorgeberechtiger();
        testSorgeberechtiger2.setSorgeberechtigerId(2l);
        testSorgeberechtiger2.setSorgeberechtigerVorname("Ernst");
        testSorgeberechtiger2.setSorgeberechtigerNachname("Emsig");

        testSorgeberechtiger2.setGeburtsdatum(LocalDate.of(1980, 9, 19));
        sorgeberechtigerRepository.save(testSorgeberechtiger2);

        Sorgeberechtiger testSorgeberechtiger3 = new Sorgeberechtiger();
        testSorgeberechtiger3.setSorgeberechtigerId(3l);
        testSorgeberechtiger3.setSorgeberechtigerVorname("Mona");
        testSorgeberechtiger3.setSorgeberechtigerNachname("Lieza");

        testSorgeberechtiger3.setGeburtsdatum(LocalDate.of(1975, 01, 04));
        sorgeberechtigerRepository.save(testSorgeberechtiger3);
    } // end testdaten


}
