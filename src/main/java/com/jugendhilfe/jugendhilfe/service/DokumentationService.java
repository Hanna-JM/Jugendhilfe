package com.jugendhilfe.jugendhilfe.service;

import com.jugendhilfe.jugendhilfe.domain.Dokumentation;
import com.jugendhilfe.jugendhilfe.domain.Kind;
import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.repository.DokumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DokumentationService {
    @Autowired
    private DokumentationRepository dokumentationRepository;

    public void save(Dokumentation dokumentation)
    {
        dokumentationRepository.save(dokumentation);
    }

//    public Dokumentation get(long id) {
//        return dokumentationRepository.findById(id).get();
//    }

//    public void delete(long id) {
//        dokumentationRepository.deleteById(id);
//    }


    public List<Dokumentation> findAll() {

        return dokumentationRepository.findAll();
        //return repoMitarbeiter.findAll(Sort.by("furtherEducationName").ascending());
    }

//    public Dokumentation getByDokumentationId(Long dokumentationId) {          // kommt von repo
//        return dokumentationRepository.findByDokumentationId(dokumentationId);
//    }

    public List<Dokumentation> findByKeyword(String keyword) {
        return dokumentationRepository.findByKeyword(keyword);
    }

    public void addOptionenDokumentationTyp(Dokumentation dokumentation){
        if (dokumentation.optionenDokumentationTyp.isEmpty()) {
            dokumentation.optionenDokumentationTyp.add("Taeglich");
            dokumentation.optionenDokumentationTyp.add("Eskalation");
//            dokumentation.optionenDokumentationTyp.add("Divers");
        }
    }

    public  void  addTestDokumentationKind(){


        Dokumentation dokumentation1 = new Dokumentation();
        dokumentation1.setDokumentationId(1L);
        dokumentation1.setDokumentationdatum(LocalDateTime.of(2023,5,29,10,52,33,074271));
        dokumentation1.setDokumentationtyp("Taeglich");
        dokumentation1.setDokumentationtext("ich ess blumen, den tiere tun mir leid");
        dokumentation1.setKind(new Kind(1L));
        dokumentationRepository.save(dokumentation1);

        Dokumentation dokumentation2 = new Dokumentation();
        dokumentation2.setDokumentationId(2L);
        dokumentation2.setDokumentationdatum(LocalDateTime.of(2023,4,12,9,32,45,074271));
        dokumentation2.setDokumentationtyp("Eskalation");
        dokumentation2.setDokumentationtext("Manni fönt das Klima heiss");
        dokumentation2.setKind(new Kind(2L));
        dokumentationRepository.save(dokumentation2);

        Dokumentation dokumentation3 = new Dokumentation();
        dokumentation3.setDokumentationId(3L);
        dokumentation3.setDokumentationdatum(LocalDateTime.of(2023,3,29,8,9,11,074271));
        dokumentation3.setDokumentationtyp("Taeglich");
        dokumentation3.setDokumentationtext("trimm dich");
        dokumentation3.setKind(new Kind(3L));
        dokumentationRepository.save(dokumentation3);

    }
}
