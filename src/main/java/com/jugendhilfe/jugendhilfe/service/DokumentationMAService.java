package com.jugendhilfe.jugendhilfe.service;

import com.jugendhilfe.jugendhilfe.domain.Dokumentation;
import com.jugendhilfe.jugendhilfe.domain.DokumentationMA;
import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.repository.DokumentationMARepository;
import com.jugendhilfe.jugendhilfe.repository.DokumentationRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DokumentationMAService {
    @Autowired
    private DokumentationMARepository dokumentationMARepository;

    public void save(DokumentationMA dokumentationma)
    {
        dokumentationMARepository.save(dokumentationma);
    }

//    public Dokumentation get(long id) {
//        return dokumentationRepository.findById(id).get();
//    }

//    public void delete(long id) {
//        dokumentationRepository.deleteById(id);
//    }


    public List<DokumentationMA> findAll() {

        return dokumentationMARepository.findAll();
        //return repoMitarbeiter.findAll(Sort.by("furtherEducationName").ascending());
    }

//    public Dokumentation getByDokumentationId(Long dokumentationId) {          // kommt von repo
//        return dokumentationRepository.findByDokumentationId(dokumentationId);
//    }

    public List<DokumentationMA> findByKeyword(String keyword) {
        return dokumentationMARepository.findByKeyword(keyword);
    }

    public void addOptionenDokumentationTyp(DokumentationMA dokumentationma){
        if (dokumentationma.optionenDokumentationTyp.isEmpty()) {
            dokumentationma.optionenDokumentationTyp.add("Normal");
            dokumentationma.optionenDokumentationTyp.add("Eskalation");
        }
    }

    public  void  addTestDokumentationMitarbeiter(){


        DokumentationMA dokumentationMA1 = new DokumentationMA();
        dokumentationMA1.setDokumentationId(1L);
        dokumentationMA1.setDokumentationdatum(LocalDateTime.of(2023,5,28,11,11,11,074271));
        dokumentationMA1.setDokumentationtyp("Eskalation");
        dokumentationMA1.setDokumentationtext("MA ist krankt, mit corona");
        dokumentationMA1.setMitarbeiter(new Mitarbeiter(1L));
        dokumentationMARepository.save(dokumentationMA1);

        DokumentationMA dokumentationMA2 = new DokumentationMA();
        dokumentationMA2.setDokumentationId(2L);
        dokumentationMA2.setDokumentationdatum(LocalDateTime.of(2023,5,29,5,5,5,074271));
        dokumentationMA2.setDokumentationtyp("Taeglich");
        dokumentationMA2.setDokumentationtext("mal wieder verschlafen");
        dokumentationMA2.setMitarbeiter(new Mitarbeiter(2L));
        dokumentationMARepository.save(dokumentationMA2);

        DokumentationMA dokumentationMA3 = new DokumentationMA();
        dokumentationMA3.setDokumentationId(3L);
        dokumentationMA3.setDokumentationdatum(LocalDateTime.of(2023,4,22,2,45,2,074271));
        dokumentationMA3.setDokumentationtyp("Taeglich");
        dokumentationMA3.setDokumentationtext("plant fortbildung in erster hilfe in kw 15");
        dokumentationMA3.setMitarbeiter(new Mitarbeiter(3L));
        dokumentationMARepository.save(dokumentationMA3);

    }
}
