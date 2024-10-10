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

        Etudiant updatedEtudiant = etudiantRepository.findById(savedEtudiant.getIdEtudiant()).get();
        assertEquals(savedDepartement.getIdDepart(), updatedEtudiant.getDepartement().getIdDepart());
    }
}
