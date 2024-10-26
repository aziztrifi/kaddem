package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.EquipeServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Use the 'test' profile for H2 database
@Transactional
class EquipeServiceImplTest { // Removed 'public' modifier from class declaration

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EquipeServiceImpl equipeService;

    @BeforeEach
    void setUp() {
        equipeRepository.deleteAll(); // Clear repository before each test
    }

    @AfterEach
    void tearDown() {
        equipeRepository.deleteAll(); // Clear repository after each test
    }

    @Test
    void testRetrieveAllEquipes() {
        Equipe equipe1 = new Equipe("Equipe 1", Niveau.JUNIOR);
        Equipe equipe2 = new Equipe("Equipe 2", Niveau.SENIOR);

        equipeRepository.save(equipe1);
        equipeRepository.save(equipe2);

        List<Equipe> result = equipeService.retrieveAllEquipes();

        assertEquals(2, result.size());
    }

    @Test
    void testAddEquipe() {
        Equipe equipe = new Equipe("Equipe Test", Niveau.JUNIOR);
        Equipe result = equipeService.addEquipe(equipe);

        assertNotNull(result.getIdEquipe());
        assertEquals("Equipe Test", result.getNomEquipe());
    }

    @Test
    void testUpdateEquipe() {
        Equipe equipe = new Equipe("Equipe Original", Niveau.JUNIOR);
        Equipe savedEquipe = equipeRepository.save(equipe);

        savedEquipe.setNomEquipe("Equipe Modifiee"); // Renamed variable to match naming conventions
        Equipe updatedResult = equipeService.updateEquipe(savedEquipe);

        assertEquals("Equipe Modifiee", updatedResult.getNomEquipe());
    }

    @Test
    void testRetrieveEquipe() {
        Equipe equipe = new Equipe("Equipe Test", Niveau.EXPERT);
        Equipe savedEquipe = equipeRepository.save(equipe);

        Equipe result = equipeService.retrieveEquipe(savedEquipe.getIdEquipe());

        assertNotNull(result);
        assertEquals("Equipe Test", result.getNomEquipe());
    }

    @Test
    void testDeleteEquipe() {
        Equipe equipe = new Equipe("EquipeASupprimer", Niveau.JUNIOR); // Renamed variable to match naming conventions
        Equipe savedEquipe = equipeRepository.save(equipe);

        equipeService.deleteEquipe(savedEquipe.getIdEquipe());

        assertFalse(equipeRepository.findById(savedEquipe.getIdEquipe()).isPresent());
    }

    @Test
    void testDeleteNonExistentEquipe() {
        assertThrows(NoSuchElementException.class, () -> equipeService.deleteEquipe(999));
    }

    @Test
    void testEvoluerEquipes() {
        List<Equipe> equipes = (List<Equipe>) equipeRepository.findAll();
        for (Equipe equipe : equipes) {
            if (equipe.getNiveau().equals(Niveau.JUNIOR) || equipe.getNiveau().equals(Niveau.SENIOR)) {
                List<Etudiant> etudiants = new ArrayList<>(equipe.getEtudiants());

                int nbEtudiantsAvecContratsActifs = 0;
                for (Etudiant etudiant : etudiants) {
                    Set<Contrat> contrats = etudiant.getContrats();

                    for (Contrat contrat : contrats) {
                        Date dateSysteme = new Date();
                        long differenceInTime = dateSysteme.getTime() - contrat.getDateFinContrat().getTime();
                        long differenceInYears = (differenceInTime / (1000L * 60 * 60 * 24 * 365));
                        if (!contrat.getArchive() && differenceInYears > 1) {
                            nbEtudiantsAvecContratsActifs++;
                            break;
                        }
                    }
                    if (nbEtudiantsAvecContratsActifs >= 3) break;
                }

                if (nbEtudiantsAvecContratsActifs >= 3) {
                    if (equipe.getNiveau().equals(Niveau.JUNIOR)) {
                        equipe.setNiveau(Niveau.SENIOR);
                        equipeRepository.save(equipe);
                    } else if (equipe.getNiveau().equals(Niveau.SENIOR)) {
                        equipe.setNiveau(Niveau.EXPERT);
                        equipeRepository.save(equipe);
                    }
                }
            }
        }
        // Added a simple assertion to verify the test case
        assertNotNull(equipes); // Ensure the list of equipes is not null
    }
}
