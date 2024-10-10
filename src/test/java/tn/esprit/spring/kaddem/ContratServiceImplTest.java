package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

import java.util.*;

public class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllContrats() {
        List<Contrat> mockContrats = new ArrayList<>();
        mockContrats.add(new Contrat(new Date(), new Date(), Specialite.CLOUD, false, 1000));
        when(contratRepository.findAll()).thenReturn(mockContrats);

        List<Contrat> result = contratService.retrieveAllContrats();
        assertEquals(1, result.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    public void testAddContrat() {
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.RESEAUX, false, 1500);
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.addContrat(contrat);
        assertEquals(contrat, result);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    public void testUpdateContrat() {
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.SECURITE, true, 2000);
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.updateContrat(contrat);
        assertEquals(contrat, result);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    public void testRetrieveContrat() {
        Integer contratId = 1;
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 2500);
        when(contratRepository.findById(contratId)).thenReturn(Optional.of(contrat));

        Contrat result = contratService.retrieveContrat(contratId);
        assertEquals(contrat, result);
        verify(contratRepository, times(1)).findById(contratId);
    }

    @Test
    public void testRemoveContrat() {
        Integer contratId = 1;
        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.IA, false, 2500);
        when(contratRepository.findById(contratId)).thenReturn(Optional.of(contrat));

        contratService.removeContrat(contratId);
        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    public void testAffectContratToEtudiant() {
        Integer contratId = 1;
        String nomE = "Doe";
        String prenomE = "John";

        Etudiant etudiant = new Etudiant();
        etudiant.setNomE(nomE);
        etudiant.setPrenomE(prenomE);

        // Initialize the contrats set with an empty HashSet
        etudiant.setContrats(new HashSet<>());

        Contrat contrat = new Contrat(new Date(), new Date(), Specialite.RESEAUX, false, 1500);

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(contratId)).thenReturn(contrat);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(contratId, nomE, prenomE);
        assertEquals(etudiant, result.getEtudiant());

        verify(etudiantRepository, times(1)).findByNomEAndPrenomE(nomE, prenomE);
        verify(contratRepository, times(1)).findByIdContrat(contratId);
        verify(contratRepository, times(1)).save(contrat);
    }

}
