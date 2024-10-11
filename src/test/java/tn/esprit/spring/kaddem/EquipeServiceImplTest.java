package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.DetailEquipe;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.EquipeServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EquipeServiceImplTest {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EquipeServiceImpl equipeService;

    @BeforeEach
    void setUp() {
        // Clear the repository before each test
        equipeRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // Clear the repository after each test
        equipeRepository.deleteAll();
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

        savedEquipe.setNomEquipe("Equipe Modifiée");
        Equipe updatedResult = equipeService.updateEquipe(savedEquipe);

        assertEquals("Equipe Modifiée", updatedResult.getNomEquipe());
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
        Equipe equipe = new Equipe("Equipe à Supprimer", Niveau.JUNIOR);
        Equipe savedEquipe = equipeRepository.save(equipe);

        equipeService.deleteEquipe(savedEquipe.getIdEquipe());

        assertFalse(equipeRepository.findById(savedEquipe.getIdEquipe()).isPresent());
    }
}
