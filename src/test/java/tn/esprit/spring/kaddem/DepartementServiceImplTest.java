package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DepartementServiceImplTest {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private DepartementServiceImpl departementService;

    @BeforeEach
    void setUp() {
        // Clear the repository before each test
        departementRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // Clear the repository after each test
        departementRepository.deleteAll();
    }

    @Test
    void testRetrieveAllDepartements() {
        Departement dep1 = new Departement();
        dep1.setNomDepart("IT");
        departementRepository.save(dep1);

        Departement dep2 = new Departement();
        dep2.setNomDepart("HR");
        departementRepository.save(dep2);

        List<Departement> result = departementService.retrieveAllDepartements();

        assertEquals(2, result.size());
    }

    @Test
    void testAddDepartement() {
        Departement departement = new Departement();
        departement.setNomDepart("Finance");

        Departement result = departementService.addDepartement(departement);

        assertNotNull(result.getIdDepart());
        assertEquals("Finance", result.getNomDepart());
    }

    @Test
    void testUpdateDepartement() {
        Departement departement = new Departement();
        departement.setNomDepart("Marketing");
        Departement savedDepartement = departementRepository.save(departement);

        savedDepartement.setNomDepart("Sales");
        Departement updatedResult = departementService.updateDepartement(savedDepartement);

        assertEquals("Sales", updatedResult.getNomDepart());
    }

    @Test
    void testRetrieveDepartement() {
        Departement departement = new Departement();
        departement.setNomDepart("Operations");
        Departement savedDepartement = departementRepository.save(departement);

        Departement result = departementService.retrieveDepartement(savedDepartement.getIdDepart());

        assertNotNull(result);
        assertEquals("Operations", result.getNomDepart());
    }

    @Test
    void testDeleteDepartement() {
        Departement departement = new Departement();
        departement.setNomDepart("Logistics");
        Departement savedDepartement = departementRepository.save(departement);

        departementService.deleteDepartement(savedDepartement.getIdDepart());

        assertFalse(departementRepository.findById(savedDepartement.getIdDepart()).isPresent());
    }
}
