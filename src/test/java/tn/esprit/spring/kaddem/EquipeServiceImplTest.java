package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.EquipeServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test") // Use the 'test' profile for H2 database
@Transactional
class EquipeServiceImplTest {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EquipeServiceImpl equipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @AfterEach
    void tearDown() {
        // tearDown method might not be needed because of the @Transactional annotation
    }

    @Test
    void testRetrieveAllEquipes() {
        equipeRepository.save(new Equipe("Equipe 1", Niveau.JUNIOR));
        equipeRepository.save(new Equipe("Equipe 2", Niveau.SENIOR));

        List<Equipe> result = equipeService.retrieveAllEquipes();

        assertEquals(2, result.size(), "Should retrieve exactly 2 equipes.");
    }

    @Test
    void testAddEquipe() {
        Equipe newEquipe = new Equipe("Equipe Test", Niveau.JUNIOR);
        Equipe savedEquipe = equipeService.addEquipe(newEquipe);

        assertNotNull(savedEquipe.getIdEquipe(), "Equipe ID should not be null after save.");
        assertEquals("Equipe Test", savedEquipe.getNomEquipe(), "Equipe name should match the saved name.");
    }

    @Test
    void testUpdateEquipe() {
        Equipe equipe = new Equipe("Equipe Original", Niveau.JUNIOR);
        equipe = equipeRepository.save(equipe);
        equipe.setNomEquipe("Equipe Modifiee");

        Equipe updatedEquipe = equipeService.updateEquipe(equipe);

        assertEquals("Equipe Modifiee", updatedEquipe.getNomEquipe(), "Equipe name should be updated.");
    }

    @Test
    void testRetrieveEquipe() {
        Equipe equipe = new Equipe("Equipe Test", Niveau.EXPERT);
        equipe = equipeRepository.save(equipe);

        Equipe fetchedEquipe = equipeService.retrieveEquipe(equipe.getIdEquipe());

        assertNotNull(fetchedEquipe, "Retrieved equipe should not be null.");
        assertEquals("Equipe Test", fetchedEquipe.getNomEquipe(), "Equipe name should match the fetched name.");
    }

    @Test
    void testDeleteEquipe() {
        Equipe equipe = new Equipe("Equipe to Delete", Niveau.JUNIOR);
        equipe = equipeRepository.save(equipe);

        equipeService.deleteEquipe(equipe.getIdEquipe());

        Optional<Equipe> deletedEquipe = equipeRepository.findById(equipe.getIdEquipe());
        assertFalse(deletedEquipe.isPresent(), "Equipe should not be found after deletion.");
    }



    @Test
    void retrieveAllEquipes_ShouldReturnListOfEquipes() {
      
        Equipe equipe1 = new Equipe();
        equipe1.setNomEquipe("Team A");
        Equipe equipe2 = new Equipe();
        equipe2.setNomEquipe("Team B");

        List<Equipe> mockEquipes = Arrays.asList(equipe1, equipe2);
        when(equipeRepository.findAll()).thenReturn(mockEquipes);

        // Act: call the service method
        List<Equipe> equipes = equipeService.retrieveAllEquipes();

        // Assert: verify the behavior and result
        assertEquals(2, equipes.size());
        assertEquals("Team A", equipes.get(0).getNomEquipe());
        assertEquals("Team B", equipes.get(1).getNomEquipe());
    }

}
