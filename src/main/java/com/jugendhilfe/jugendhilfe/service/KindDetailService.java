package com.jugendhilfe.jugendhilfe.service;

import com.jugendhilfe.jugendhilfe.domain.*;
import com.jugendhilfe.jugendhilfe.repository.KindDetailRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class KindDetailService {
    @Autowired
    private KindDetailRepository kindDetailRepository;

    public void save(KindDetail kindDetail) {
        kindDetailRepository.save(kindDetail);
    }

    public Optional<KindDetail> get(long id) {
        return kindDetailRepository.findById(id);
    }

    public void delete(long id) {
        kindDetailRepository.deleteById(id);
    }


    public List<KindDetail> findAll() {
        return kindDetailRepository.findAll();
    }

    // Methode zum Überprüfen und Aktualisieren des Felds "aktiv"
    public void updateAktivField() {
        List<KindDetail> kindDetails = findAll();
        LocalDate currentDate = LocalDate.now();

        for (KindDetail kindDetail : kindDetails) {
            LocalDate auszug = kindDetail.getAuszug();
            boolean isActive = auszug == null || auszug.isAfter(currentDate) || auszug.isEqual(currentDate);
            kindDetail.setAktiv(isActive);
        }
        kindDetailRepository.saveAll(kindDetails);
    }

    public List<KindDetail> findAllAktiveKind() {
        return kindDetailRepository.findByAktivTrue();
    }

    public KindDetail getByKindDetailId(long kindDetailId) {
        return kindDetailRepository.findByKindDetailId(kindDetailId);
    }

    public List<KindDetail> findByKeyword(String keyword) {
        return kindDetailRepository.findByKeyword(keyword);
    }


    public void addTestKindDetail() {

        KindDetail kindDetail1 = new KindDetail();
        kindDetail1.setKindDetailId(1L);
        kindDetail1.setEinzug(LocalDate.of(2023, 5, 1));
        kindDetail1.setAuszug(LocalDate.of(2023, 5, 5));
        kindDetail1.setAktiv(true);
        kindDetail1.setKindDetailKind(new Kind(1L));
        kindDetail1.setKindDetailWohngruppe(new Wohngruppe(1L));
        kindDetailRepository.save(kindDetail1);

        KindDetail kindDetail2 = new KindDetail();
        kindDetail2.setKindDetailId(2L);
        kindDetail2.setEinzug(LocalDate.of(2023, 4, 1));
        kindDetail2.setAuszug(LocalDate.of(2023, 4, 29));
        kindDetail2.setAktiv(false);
        kindDetail2.setKindDetailKind(new Kind(2L));
        kindDetail2.setKindDetailWohngruppe(new Wohngruppe(2L));
        kindDetailRepository.save(kindDetail2);

        KindDetail kindDetail3 = new KindDetail();
        kindDetail3.setKindDetailId(3L);
        kindDetail3.setEinzug(LocalDate.of(2023, 3, 22));
        kindDetail3.setAuszug(LocalDate.of(2023, 3, 29));
        kindDetail3.setAktiv(true);
        kindDetail3.setKindDetailKind(new Kind(3L));
        kindDetail3.setKindDetailWohngruppe(new Wohngruppe(3L));
        kindDetailRepository.save(kindDetail3);

    } // end testdaten
}

