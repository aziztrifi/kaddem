package tn.esprit.spring.kaddem.services;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ContratServiceImpl implements IContratService {

    private final ContratRepository contratRepository;
    private final EtudiantRepository etudiantRepository;

    public ContratServiceImpl(ContratRepository contratRepository, EtudiantRepository etudiantRepository) {
        this.contratRepository = contratRepository;
        this.etudiantRepository = etudiantRepository;
    }

    @Override
    public List<Contrat> retrieveAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat updateContrat(Contrat ce) {
        return contratRepository.save(ce);
    }

    @Override
    public Contrat addContrat(Contrat ce) {
        return contratRepository.save(ce);
    }

    @Override
    public Contrat retrieveContrat(Integer idContrat) {
        return contratRepository.findById(idContrat)
                .orElseThrow(() -> new IllegalArgumentException("Contrat not found with id: " + idContrat));
    }

    @Override
    public void removeContrat(Integer idContrat) {
        Contrat c = retrieveContrat(idContrat);
        contratRepository.delete(c);
    }

    @Override
    public Contrat affectContratToEtudiant(Integer idContrat, String nomE, String prenomE) {
        Etudiant e = etudiantRepository.findByNomEAndPrenomE(nomE, prenomE);
        if (e == null) {
            throw new IllegalArgumentException("Etudiant not found with name: " + nomE + " " + prenomE);
        }

        Contrat ce = contratRepository.findByIdContrat(idContrat);
        if (ce == null) {
            throw new IllegalArgumentException("Contrat not found with id: " + idContrat);
        }

        Set<Contrat> contrats = e.getContrats();
        long activeContractsCount = contrats.stream()
                .filter(contrat -> Boolean.TRUE.equals(contrat.getArchive()))
                .count();

        // Ensure active contracts do not exceed the limit
        if (activeContractsCount < 5) {
            ce.setEtudiant(e);
            contratRepository.save(ce);
        } else {
            log.warn("Etudiant already has the maximum number of active contracts.");
        }
        return ce;
    }

    @Override
    public Integer nbContratsValides(Date startDate, Date endDate) {
        return contratRepository.getnbContratsValides(startDate, endDate);
    }

    @Override
    public void retrieveAndUpdateStatusContrat() {
        List<Contrat> contrats = contratRepository.findAll();
        Date currentDate = new Date();

        for (Contrat contrat : contrats) {
            if (!Boolean.TRUE.equals(contrat.getArchive())) {
                long differenceInDays = (currentDate.getTime() - contrat.getDateFinContrat().getTime()) / (1000 * 60 * 60 * 24);

                if (differenceInDays == 15) {
                    log.info("15 days before expiration for Contrat: " + contrat);
                    // Add further actions if needed, e.g., send notification
                } else if (differenceInDays == 0) {
                    contrat.setArchive(true);
                    contratRepository.save(contrat);
                    log.info("Contract archived for Contrat: " + contrat);
                }
            }
        }
    }

    @Override
    public float getChiffreAffaireEntreDeuxDates(Date startDate, Date endDate) {
        long differenceInDays = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
        float differenceInMonths = differenceInDays / 30f;

        List<Contrat> contrats = contratRepository.findAll();
        float totalRevenue = 0;

        for (Contrat contrat : contrats) {
            float contractRevenue = 0;
            switch (contrat.getSpecialite()) {
                case IA:
                    contractRevenue = differenceInMonths * 300;
                    break;
                case CLOUD:
                    contractRevenue = differenceInMonths * 400;
                    break;
                case RESEAUX:
                    contractRevenue = differenceInMonths * 350;
                    break;
                default:
                    contractRevenue = differenceInMonths * 450;
                    break;
            }
            totalRevenue += contractRevenue;
        }
        return totalRevenue;
    }
}
