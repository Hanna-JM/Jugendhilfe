package com.jugendhilfe.jugendhilfe.service;

import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.domain.Wohngruppe;
import com.jugendhilfe.jugendhilfe.repository.MitarbeiterRepository;
import com.jugendhilfe.jugendhilfe.repository.WohngruppenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WohngruppenService {
    @Autowired
    private WohngruppenRepository repoWohngruppen;
    private MitarbeiterRepository mitarbeiterRepository;

    public void save(Wohngruppe wohngruppe) {
        repoWohngruppen.save(wohngruppe);
    }

    public Wohngruppe get(long id) {
        return repoWohngruppen.findById(id).get();
    }

    public void delete(long id) {
        repoWohngruppen.deleteById(id);
    }

    public List<Wohngruppe> findAll() {
        return repoWohngruppen.findAll();
    }

    public Page<Wohngruppe> findAll(Pageable pageable) {
        return repoWohngruppen.findAll(pageable);
    }


    public List<Wohngruppe> findAllSorted(String sortBy, Pageable pageable) {
        Sort sort = Sort.by(sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return repoWohngruppen.findAll(sortedPageable).getContent();
    }
//    public List<Wohngruppe> findAllSorted(String sortBy) {
//        Sort sort = Sort.by(sortBy);
//        return repoWohngruppen.findAll(sort);
//    }

//    public List<Wohngruppe> findAllSorted(String sortBy, String sortDirection) {
//        Sort.Direction direction = Sort.Direction.ASC;
//
//        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
//            direction = Sort.Direction.DESC;
//        }
//
//        Sort sort = Sort.by(direction, sortBy);
//        return repoWohngruppen.findAll(sort);
//    }

    public List<Mitarbeiter> getAllMitarbeiter() {
        return mitarbeiterRepository.findAll();
    }

    public Mitarbeiter getMitarbeiter(long mitarbeiterId) {
        return mitarbeiterRepository.findByMitarbeiterId(mitarbeiterId);
    }

    public Wohngruppe getWohngruppenId(Long wohngruppenId) {
        return repoWohngruppen.findByWohngruppenId(wohngruppenId);
    }

//    public List<Wohngruppe> findByKeyword(String keyword) {
//        return repoWohngruppen.findByKeyword(keyword);
//    }
//    public List<Wohngruppe> findByKeyword(String keyword, Pageable pageable) {
//        return repoWohngruppen.findByKeyword(keyword, pageable);
//    }
    public Page<Wohngruppe> findByKeyword(String keyword, Pageable pageable) {
        return repoWohngruppen.findByKeyword(keyword, pageable);
    }


    @Autowired
    public void setMitarbeiterRepository(MitarbeiterRepository mitarbeiterRepository) {
        this.mitarbeiterRepository = mitarbeiterRepository;
    }

    public Wohngruppe findById(Long wohngruppenId) {
        return repoWohngruppen.findById(wohngruppenId).orElse(null);
    }


    // TODO vor Auslieferung entfernen
    // bei neustart oder zwischendurch, datenbank mit testdaten füllen, vor Auslieferung entfernen
    public void addTestWohngruppe() {

        Wohngruppe testWohnguppe1 = new Wohngruppe();
        testWohnguppe1.setWohngruppenId(1l);
        testWohnguppe1.setWohngruppenName("Klee");
        testWohnguppe1.setStrasse("am deich");
        testWohnguppe1.setPlz("28485");
        testWohnguppe1.setOrt("Bremen");
        testWohnguppe1.setAnzahlPlaetze(6);
        testWohnguppe1.setTagessatz(56);
        repoWohngruppen.save(testWohnguppe1);

        Wohngruppe testWohnguppe2 = new Wohngruppe();
        testWohnguppe2.setWohngruppenId(2l);
        testWohnguppe2.setWohngruppenName("Sonne");
        testWohnguppe2.setStrasse("Wolkengasse");
        testWohnguppe2.setPlz("28623");
        testWohnguppe2.setOrt("Huchting");
        testWohnguppe2.setAnzahlPlaetze(10);
        testWohnguppe2.setTagessatz(49);
        repoWohngruppen.save(testWohnguppe2);

        Wohngruppe testWohnguppe3 = new Wohngruppe();
        testWohnguppe3.setWohngruppenId(3l);
        testWohnguppe3.setWohngruppenName("Hut");
        testWohnguppe3.setStrasse("Macher-Allee");
        testWohnguppe3.setPlz("21485");
        testWohnguppe3.setOrt("Steilshoop");
        testWohnguppe3.setAnzahlPlaetze(7);
        testWohnguppe3.setTagessatz(43);
        repoWohngruppen.save(testWohnguppe3);
    } // end testdaten

}
