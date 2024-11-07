package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EtudiantServiceImplTest {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    void setUp() {
        // Clear the repositories before each test
        etudiantRepository.deleteAll();
        departementRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // Clear the repositories after each test
        etudiantRepository.deleteAll();
        departementRepository.deleteAll();
    }

    @Test
    void testRetrieveAllEtudiants() {
        Etudiant etudiant1 = new Etudiant("John", "Doe");
        Etudiant etudiant2 = new Etudiant("Jane", "Doe");
        etudiantRepository.save(etudiant1);
        etudiantRepository.save(etudiant2);

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        assertEquals(2, result.size());
        assertTrue(result.contains(etudiant1));
        assertTrue(result.contains(etudiant2));
    }

    @Test
    void testAddEtudiant() {
        Etudiant etudiant = new Etudiant("Alice", "Wonderland");

        Etudiant result = etudiantService.addEtudiant(etudiant);

        assertNotNull(result.getIdEtudiant());
        assertEquals("Alice", result.getNomE());
        assertEquals("Wonderland", result.getPrenomE());
    }

    @Test
    void testUpdateEtudiant() {
        Etudiant etudiant = new Etudiant("Mark", "Twain");
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        savedEtudiant.setNomE("Samuel");
        Etudiant updatedResult = etudiantService.updateEtudiant(savedEtudiant);

        assertEquals("Samuel", updatedResult.getNomE());
        assertEquals(savedEtudiant.getIdEtudiant(), updatedResult.getIdEtudiant());
    }

    @Test
    void testRetrieveEtudiant() {
        Etudiant etudiant = new Etudiant("Clark", "Kent");
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        Etudiant result = etudiantService.retrieveEtudiant(savedEtudiant.getIdEtudiant());

        assertNotNull(result);
        assertEquals("Clark", result.getNomE());
    }

    @Test
    void testDeleteEtudiant() {
        Etudiant etudiant = new Etudiant("Bruce", "Wayne");
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        etudiantService.removeEtudiant(savedEtudiant.getIdEtudiant());

        assertFalse(etudiantRepository.findById(savedEtudiant.getIdEtudiant()).isPresent());
    }

    @Test
    void testAssignEtudiantToDepartement() {
        Etudiant etudiant = new Etudiant("Tony", "Stark");
        Departement departement = new Departement();
        departement.setNomDepart("Engineering");
        Departement savedDepartement = departementRepository.save(departement);

        Etudiant savedEtudiant = etudiantRepository.save(etudiant);
        etudiantService.assignEtudiantToDepartement(savedEtudiant.getIdEtudiant(), savedDepartement.getIdDepart());

        Etudiant updatedEtudiant = etudiantRepository.findById(savedEtudiant.getIdEtudiant()).orElse(null);
        assertNotNull(updatedEtudiant);
        assertEquals(savedDepartement.getIdDepart(), updatedEtudiant.getDepartement().getIdDepart());
    }

    @Test
    void testRetrieveEtudiant_NonExistant() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiantService.retrieveEtudiant(999);
        });
        assertEquals("Étudiant non trouvé avec l'ID: 999", exception.getMessage());
    }

    @Test
    void testAssignEtudiantToDepartement_NonExistantEtudiant() {
        Departement departement = new Departement();
        departement.setNomDepart("Engineering");
        Departement savedDepartement = departementRepository.save(departement);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiantService.assignEtudiantToDepartement(999, savedDepartement.getIdDepart());
        });
        assertEquals("Étudiant non trouvé avec l'ID: 999", exception.getMessage());
    }

    @Test
    void testAssignEtudiantToDepartement_NonExistantDepartement() {
        Etudiant etudiant = new Etudiant("Bruce", "Wayne");
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            etudiantService.assignEtudiantToDepartement(savedEtudiant.getIdEtudiant(), 999);
        });
        assertEquals("Département non trouvé avec l'ID: 999", exception.getMessage());
    }
}
