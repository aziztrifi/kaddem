package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.DetailEquipe;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.EquipeServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EquipeServiceImplMockTest {

    @InjectMocks
    private EquipeServiceImpl equipeService;

    @Mock
    private EquipeRepository equipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllEquipes() {
        Equipe equipe1 = new Equipe(1, "Equipe 1", Niveau.JUNIOR);
        Equipe equipe2 = new Equipe(2, "Equipe 2", Niveau.SENIOR);
        List<Equipe> equipes = Arrays.asList(equipe1, equipe2);

        when(equipeRepository.findAll()).thenReturn(equipes);

        List<Equipe> result = equipeService.retrieveAllEquipes();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getIdEquipe());
        assertEquals(2, result.get(1).getIdEquipe());
        verify(equipeRepository, times(1)).findAll();
    }

    @Test
    void testAddEquipe() {
        Equipe equipe = new Equipe("Equipe Test", Niveau.JUNIOR);

        when(equipeRepository.save(any(Equipe.class))).thenReturn(equipe);

        Equipe result = equipeService.addEquipe(equipe);

        assertEquals("Equipe Test", result.getNomEquipe());
        verify(equipeRepository, times(1)).save(equipe);
    }

    @Test
    void testUpdateEquipe() {
        Equipe equipe = new Equipe(1, "Equipe Updated", Niveau.SENIOR);

        when(equipeRepository.save(any(Equipe.class))).thenReturn(equipe);

        Equipe result = equipeService.updateEquipe(equipe);

        assertEquals("Equipe Updated", result.getNomEquipe());
        verify(equipeRepository, times(1)).save(equipe);
    }

    @Test
    void testRetrieveEquipe() {
        Integer equipeId = 1;
        Equipe equipe = new Equipe(equipeId, "Equipe Test", Niveau.EXPERT);

        when(equipeRepository.findById(equipeId)).thenReturn(Optional.of(equipe));

        Equipe result = equipeService.retrieveEquipe(equipeId);

        assertEquals("Equipe Test", result.getNomEquipe());
        assertEquals(Niveau.EXPERT, result.getNiveau());
        verify(equipeRepository, times(1)).findById(equipeId);
    }

    @Test
    void testDeleteEquipe() {
        Integer equipeId = 1;
        Equipe equipe = new Equipe(equipeId, "Equipe to Delete", Niveau.JUNIOR);

        when(equipeRepository.findById(equipeId)).thenReturn(Optional.of(equipe));

        equipeService.deleteEquipe(equipeId);

        verify(equipeRepository, times(1)).delete(equipe);
    }

    // Example for testing evoluerEquipes() could be added here if needed

    @Test
    public void testEquipeDoesNotEvolueDueToInsufficientActiveContracts() {
        // Initialize the test data
        Equipe equipe = new Equipe();
        equipe.setNiveau(Niveau.JUNIOR);

        // Create students with fewer than 3 active contracts (using List instead of Set)
        List<Etudiant> etudiants = new ArrayList<>();
        for (int i = 0; i < 2; i++) { // Exactly 2 students with active contracts
            Etudiant etudiant = new Etudiant();
            Contrat contratActif = new Contrat();
            contratActif.setArchive(false); // Active contract
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -2); // Contract valid for more than one year
            contratActif.setDateFinContrat(cal.getTime());
            etudiant.setContrats(Set.of(contratActif));  // Keep this as a Set for contracts
            etudiants.add(etudiant);  // Add to List
        }

        equipe.setEtudiants(new HashSet<>(etudiants));  // Converting List to Set

        // Simulate the repository behavior
        when(equipeRepository.findAll()).thenReturn(List.of(equipe));  // Ensure List is returned

    }
}
