package com.jugendhilfe.jugendhilfe.service;

import com.jugendhilfe.jugendhilfe.domain.Mitarbeiter;
import com.jugendhilfe.jugendhilfe.domain.MitarbeiterDetail;
import com.jugendhilfe.jugendhilfe.domain.Wohngruppe;
import com.jugendhilfe.jugendhilfe.repository.MitarbeiterDetailRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MitarbeiterDetailService {

    @Autowired
    private MitarbeiterDetailRepository mitarbeiterDetailRepository;
    public  void save (MitarbeiterDetail mitarbeiterdetail){
        mitarbeiterDetailRepository.save(mitarbeiterdetail);
    }
    public Optional<MitarbeiterDetail> get(long id ){return mitarbeiterDetailRepository.findById(id);
    }
    public void delete(long id){mitarbeiterDetailRepository.deleteById(id);}

    public List<MitarbeiterDetail> findAll(){
        return mitarbeiterDetailRepository.findAll();
    }
    // Methode zum Überprüfen und Aktualisieren des Felds "aktiv"
    public void updateAktivField() {
        List<MitarbeiterDetail> mitarbeiterDetails = findAll();
        LocalDate currentDate = LocalDate.now();
        for (MitarbeiterDetail mitarbeiterDetail : mitarbeiterDetails) {
            LocalDate einsatzEnde = mitarbeiterDetail.getEinsatzEnde();
            boolean isActive = einsatzEnde == null
                    || einsatzEnde.isAfter(currentDate)
                    || einsatzEnde.isEqual(currentDate);
            mitarbeiterDetail.setAktiv(isActive);
        }
        mitarbeiterDetailRepository.saveAll(mitarbeiterDetails);
    }
    public List<MitarbeiterDetail> findAllAktiveMitarbeiter() {
        return mitarbeiterDetailRepository.findByAktivTrue();
    }
    public MitarbeiterDetail getByMitarbeiterDetailId(long mitarbeiterDetailId){
        return mitarbeiterDetailRepository.findByMitarbeiterDetailId(mitarbeiterDetailId);
    }

    public List<MitarbeiterDetail> findByKeyword(String keyword){
        return mitarbeiterDetailRepository.findByKeyword(keyword);
    }
//    public boolean hasActiveEntryForMitarbeiter(Mitarbeiter mitarbeiter) {
//        // Überprüfen, ob ein aktiver Eintrag für den Mitarbeiter vorhanden ist
//        return mitarbeiterDetailRepository.existsByMitarbeiterDetailMitarbeiterAndAktivTrue(mitarbeiter);
//    }
//public boolean hasActiveEntryForMitarbeiter(Mitarbeiter mitarbeiter) {
//    List<MitarbeiterDetail> mitarbeiterDetails = mitarbeiter.getMitarbeitermitarbeiterDetails();
//    LocalDate today = LocalDate.now();
//
//    for (MitarbeiterDetail detail : mitarbeiterDetails) {
//        if (detail.isAktiv() && detail.getEinsatzEnde() != null && detail.getEinsatzEnde().isAfter(today)) {
//            return true;
//        }
//    }
//    return false;
//}
    public boolean hasActiveEntryForMitarbeiter(Mitarbeiter mitarbeiter) {
        List<MitarbeiterDetail> mitarbeiterDetails = mitarbeiter.getMitarbeitermitarbeiterDetails();
        for (MitarbeiterDetail mitarbeiterDetail : mitarbeiterDetails) {
            if (mitarbeiterDetail.isAktiv()) {
                // Es gibt bereits einen aktiven Eintrag
                return true;
            }
        }
        // Es gibt keinen aktiven Eintrag
        return false;
    }

    public void addTestMitarbeiterDetail(){

        MitarbeiterDetail mitarbeiterDetail1 = new MitarbeiterDetail();
        mitarbeiterDetail1.setMitarbeiterDetailId(1L);
        mitarbeiterDetail1.setEinsatzBeginn(LocalDate.of(2023,4,6));
        mitarbeiterDetail1.setEinsatzEnde(LocalDate.of(2023,5,29));
        mitarbeiterDetail1.setAktiv(true);
        mitarbeiterDetail1.setMitarbeiterDetailMitarbeiter(new Mitarbeiter(1L));
        mitarbeiterDetail1.setMitarbeiterDetailWohngruppe(new Wohngruppe(1L));
        mitarbeiterDetailRepository.save(mitarbeiterDetail1);

        MitarbeiterDetail mitarbeiterDetail2 = new MitarbeiterDetail();
        mitarbeiterDetail2.setMitarbeiterDetailId(2L);
        mitarbeiterDetail2.setEinsatzBeginn(LocalDate.of(2023,5,9));
        mitarbeiterDetail2.setEinsatzEnde(LocalDate.of(2023,5,30));
        mitarbeiterDetail2.setAktiv(true);
        mitarbeiterDetail2.setMitarbeiterDetailMitarbeiter(new Mitarbeiter(2L));
        mitarbeiterDetail2.setMitarbeiterDetailWohngruppe(new Wohngruppe(2L));
        mitarbeiterDetailRepository.save(mitarbeiterDetail2);

        MitarbeiterDetail mitarbeiterDetail3 = new MitarbeiterDetail();
        mitarbeiterDetail3.setMitarbeiterDetailId(3L);
        mitarbeiterDetail3.setEinsatzBeginn(LocalDate.of(2023,2,14));
        mitarbeiterDetail3.setEinsatzEnde(LocalDate.of(2023,5,31));
        mitarbeiterDetail3.setAktiv(true);
        mitarbeiterDetail3.setMitarbeiterDetailMitarbeiter(new Mitarbeiter(3L));
        mitarbeiterDetail3.setMitarbeiterDetailWohngruppe(new Wohngruppe(3L));
        mitarbeiterDetailRepository.save(mitarbeiterDetail3);

    } // end testdaten
}
