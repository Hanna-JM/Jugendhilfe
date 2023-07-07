package com.jugendhilfe.jugendhilfe.service;

import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.domain.Sorgeberechtiger;
import com.jugendhilfe.jugendhilfe.domain.YouthWelfareOffice;
import com.jugendhilfe.jugendhilfe.repository.KindRepository;
import com.jugendhilfe.jugendhilfe.repository.SorgeberechtigerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class KindService {
    @Autowired
    private KindRepository kindRepository;
    @Autowired
    private SorgeberechtigerRepository sorgeberechtigerRepository;

    public void save(Kind kind) {
        kindRepository.save(kind);
    }

    /**
     * Optional is primarily intended for use as a method return type where there is
     * a clear need to represent "no result," and where using null is likely to cause errors.
     * A variable whose type is Optional should never itself be null; it should always point to an Optional instance.
     *
     * @param id
     * @return
     */
    public Kind get(Long id) {
        Optional<Kind> kindOptional = kindRepository.findById(id);
        if (kindOptional.isPresent()) {
            return kindOptional.get();
        } else {
            throw new IllegalArgumentException("Kind with ID " + id + " not found.");
        }
    }


    public void delete(long id) {
        kindRepository.deleteById(id);
    }

    public List<Kind> listAll() {
        return kindRepository.findAll();
    }

    public List<Kind> findByKeyword(String keyword) {
        return kindRepository.findByKeyword(keyword);
    }

    public void addSorgeberechtigerToKind(Long kindId, Long sorgeberechtigerId) {

        Kind kind = kindRepository.findById(kindId).orElseThrow(() ->
                new IllegalArgumentException("Kind not found due to Invalid kind ID: " + kindId));
        if (kind != null) {
            Sorgeberechtiger sorgeberechtiger = sorgeberechtigerRepository.findById(sorgeberechtigerId).orElseThrow(()
                    -> new EntityNotFoundException("Sorgeberechtiger not found with Id: " + sorgeberechtigerId));

            Set<Sorgeberechtiger> sorgeberechtigerSet = kind.getSorgeberechtigerSet();
            if (!sorgeberechtigerSet.isEmpty()) {

                sorgeberechtigerSet.add(sorgeberechtiger);

            } else {
                sorgeberechtigerSet.add(sorgeberechtiger);
            }

            kind.setSorgeberechtigerSet(kind.getSorgeberechtigerSet());
            Set<Kind> sogerberechtigerKinderSet = sorgeberechtiger.getKinderSet();
            if (!sogerberechtigerKinderSet.isEmpty()) {
                sogerberechtigerKinderSet.add(kind);
            } else {
                sogerberechtigerKinderSet.add(kind);
            }
            sorgeberechtiger.setKinderSet(sogerberechtigerKinderSet);
            kindRepository.save(kind);
        }
    }

    /**
     * This method removes the Sorgeberechtiger from the Kind's sorgeberechtiger set.
     * This method  also removes the Kind object from the Sorgeberechtiger's kinder set.
     *
     * @param kindId
     * @param sorgeberechtigerId
     */
    public void removeSorgeberechtigerFromKind(Long kindId, Long sorgeberechtigerId) {
        Kind kind = kindRepository.findById(kindId).orElseThrow(() ->
                new IllegalArgumentException("Kind not found due to Invalid kind ID: " + kindId));
        if (kind != null) {
            Set<Sorgeberechtiger> sorgeberechtigerSet = kind.getSorgeberechtigerSet();
            for (Sorgeberechtiger s : sorgeberechtigerSet) {
                if (s.getSorgeberechtigerId().equals(sorgeberechtigerId)) {
                    sorgeberechtigerSet.remove(s);
                    s.getKinderSet().remove(kind);
                    kindRepository.save(kind);
                    break;
                }
            }
        }
    }

    public Kind findById(Long kindId) {
        return kindRepository.findById(kindId).orElse(null);
    }

    // TODO vor Auslieferung entfernen
    // bei neustart oder zwischendurch, datenbank mit testdaten füllen, vor Auslieferung entfernen
    public void addTestKind() {


        Sorgeberechtiger sorgeberechtiger1 = new Sorgeberechtiger("Willi","Winzig", LocalDate.of(1999,5,24));
        Sorgeberechtiger sorgeberechtiger2 = new Sorgeberechtiger("Ernst","Emsig",LocalDate.of(1980,9,19));
        Sorgeberechtiger sorgeberechtiger3 = new Sorgeberechtiger("Mona","Lieza",LocalDate.of(1975,1,4));

        YouthWelfareOffice youthWelfareOffice1 = new YouthWelfareOffice(1L);
        YouthWelfareOffice youthWelfareOffice2 = new YouthWelfareOffice(2L);
        YouthWelfareOffice youthWelfareOffice3 = new YouthWelfareOffice(3L);

        Kind testKind1 = new Kind();
        testKind1.setKindId(1L);
        testKind1.setKindVorname("Steffi");
        testKind1.setKindNachname("Oertli");
        testKind1.setKindGeschlecht("weiblich");
        testKind1.setEinzug_datum(LocalDate.of(2023, 4, 12));
        testKind1.setGeburtsdatum(LocalDate.of(2013, 12, 12));
  //      testKind1.setJugendamt("Bremen Mitte");
        testKind1.setKindYouthWelfareOffice(youthWelfareOffice1);
        testKind1.addSorgeBerechtigter(sorgeberechtiger1);
        kindRepository.save(testKind1);

        Kind testKind2 = new Kind();
        testKind2.setKindId(2L);
        testKind2.setKindVorname("Stefan");
        testKind2.setKindNachname("Weltlig");
        testKind2.setKindGeschlecht("männlich");
        testKind2.setEinzug_datum(LocalDate.of(2022, 12, 23));
        testKind2.setGeburtsdatum(LocalDate.of(2012, 12, 12));
   //     testKind2.setJugendamt("Hamburg Blankeneese");
        testKind2.setKindYouthWelfareOffice(youthWelfareOffice2);
        testKind2.addSorgeBerechtigter(sorgeberechtiger2);
        kindRepository.save(testKind2);

        Kind testKind3 = new Kind();
        testKind3.setKindId(3L);
        testKind3.setKindVorname("Anne");
        testKind3.setKindNachname("Theke");
        testKind3.setKindGeschlecht("divers");
        testKind3.setEinzug_datum(LocalDate.of(2022, 12, 23));
        testKind3.setGeburtsdatum(LocalDate.of(2011, 12, 12));
    //    testKind3.setJugendamt("Hamburg Blankeneese");
        testKind3.setKindYouthWelfareOffice(youthWelfareOffice3);
        testKind3.addSorgeBerechtigter(sorgeberechtiger3);
        kindRepository.save(testKind3);

    } // end testdaten



}
