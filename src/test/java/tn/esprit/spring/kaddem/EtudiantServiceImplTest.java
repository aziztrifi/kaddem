package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        etudiant = new Etudiant("John", "Doe");
    }

    @Test
    void testRetrieveAllEtudiants() {
        etudiantService.retrieveAllEtudiants();
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testAddEtudiant() {
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);
        assertNotNull(savedEtudiant);
        assertEquals("John", savedEtudiant.getNomE());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testUpdateEtudiant() {
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);
        Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant);
        assertNotNull(updatedEtudiant);
        assertEquals("John", updatedEtudiant.getNomE());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testRetrieveEtudiant() {
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        Etudiant foundEtudiant = etudiantService.retrieveEtudiant(1);
        assertNotNull(foundEtudiant);
        assertEquals("John", foundEtudiant.getNomE());
        verify(etudiantRepository, times(1)).findById(1);
    }

    @Test
    void testRemoveEtudiant() {
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        etudiantService.removeEtudiant(1);
        verify(etudiantRepository, times(1)).delete(etudiant);
    }

    @Test
    void testAssignEtudiantToDepartement() {
        Departement departement = new Departement();
        when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));

        etudiantService.assignEtudiantToDepartement(1, 1);

        assertEquals(departement, etudiant.getDepartement());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

  /*  @Test
    void testAddAndAssignEtudiantToEquipeAndContract() {
        Contrat contrat = new Contrat();
        Equipe equipe = new Equipe();
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        Etudiant assignedEtudiant = etudiantService.addAndAssignEtudiantToEquipeAndContract(etudiant, 1, 1);

        assertNotNull(assignedEtudiant);
        assertEquals(etudiant, contrat.getEtudiant());
        assertTrue(equipe.getEtudiants().contains(etudiant));
        verify(etudiantRepository, never()).save(any(Etudiant.class)); // Save is not called on etudiantRepository directly
    }*/
}
