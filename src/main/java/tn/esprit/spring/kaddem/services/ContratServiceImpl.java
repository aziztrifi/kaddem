package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ContratServiceImpl implements IContratService{
    @Autowired
    ContratRepository contratRepository;
    @Autowired
    EtudiantRepository etudiantRepository;
    public List<Contrat> retrieveAllContrats(){
        return (List<Contrat>) contratRepository.findAll();
    }

    public Contrat updateContrat (Contrat  ce){
        return contratRepository.save(ce);
    }

    public  Contrat addContrat (Contrat ce){
        return contratRepository.save(ce);
    }

    public Contrat retrieveContrat (Integer  idContrat){
        return contratRepository.findById(idContrat).orElse(null);
    }

    public  void removeContrat(Integer idContrat){
        Contrat c=retrieveContrat(idContrat);
        contratRepository.delete(c);
    }



    public Contrat affectContratToEtudiant(Integer idContrat, String nomE, String prenomE) {
        Etudiant etudiant = etudiantRepository.findByNomEAndPrenomE(nomE, prenomE);
        Contrat contratToAssign = contratRepository.findByIdContrat(idContrat);

        if (etudiant == null || contratToAssign == null) {
            throw new IllegalArgumentException("Etudiant or Contrat not found");
        }
        long activeContractsCount = etudiant.getContrats().stream()
                .filter(contrat -> contrat.getArchive() != null && contrat.getArchive())
                .count();
        if (contratToAssign.getArchive() != null && contratToAssign.getArchive()) {
            activeContractsCount++;
        }
        if (activeContractsCount <= 4) {
            contratToAssign.setEtudiant(etudiant);
            contratRepository.save(contratToAssign);
        }

        return contratToAssign;
    }


    public 	Integer nbContratsValides(Date startDate, Date endDate){
        return contratRepository.getnbContratsValides(startDate, endDate);
    }

    public void retrieveAndUpdateStatusContrat() {
        List<Contrat> contrats = contratRepository.findAll();
        List<Contrat> contrats15Days = new ArrayList<>();
        List<Contrat> contratsToArchive = new ArrayList<>();
        Date currentDate = new Date();

        for (Contrat contrat : contrats) {
            if (!Boolean.TRUE.equals(contrat.getArchive())) {
                long timeDifference = currentDate.getTime() - contrat.getDateFinContrat().getTime();
                long daysDifference = timeDifference / (1000 * 60 * 60 * 24);

                if (daysDifference == 15) {
                    contrats15Days.add(contrat);
                    log.info("Contrat nearing expiration: " + contrat);
                }

                if (daysDifference == 0) {
                    contratsToArchive.add(contrat);
                    contrat.setArchive(true);
                    contratRepository.save(contrat);
                }
            }
        }
    }
    public float getChiffreAffaireEntreDeuxDates(Date startDate, Date endDate) {
        final int IA_RATE = 300;
        final int CLOUD_RATE = 400;
        final int RESEAUX_RATE = 350;
        final int SECURITE_RATE = 450;
        final int DAYS_IN_MONTH = 30;

        // Calculate the difference in days and months
        long timeDifference = endDate.getTime() - startDate.getTime();
        float daysDifference = timeDifference / (1000 * 60 * 60 * 24);
        float monthsDifference = daysDifference / DAYS_IN_MONTH;

        // Calculate the revenue for the period
        float revenue = 0;
        List<Contrat> contrats = contratRepository.findAll();
        for (Contrat contrat : contrats) {
            switch (contrat.getSpecialite()) {
                case IA:
                    revenue += monthsDifference * IA_RATE;
                    break;
                case CLOUD:
                    revenue += monthsDifference * CLOUD_RATE;
                    break;
                case RESEAUX:
                    revenue += monthsDifference * RESEAUX_RATE;
                    break;
                case SECURITE:
                    revenue += monthsDifference * SECURITE_RATE;
                    break;
                default:
                    break;
            }
        }
        return revenue;
    }
}