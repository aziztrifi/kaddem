package tn.esprit.spring.kaddem;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.SubDepartement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.SubDepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;
import tn.esprit.spring.kaddem.services.SubDepartementServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubDepartementServiceImplTest {
    @Autowired
    private SubDepartementRepository departementRepository;

    @Autowired
    private SubDepartementServiceImpl departementService;
    @Autowired
    private DepartementServiceImpl depart;

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
    void testRetrieveAllSubDepartements() {
        SubDepartement subDepartement1 = new SubDepartement();
        subDepartement1.setNomDepart("ITIT");
        subDepartement1.setDescription("TestTest");
        Departement dep1 = new Departement();
        dep1.setNomDepart("IT");
        subDepartement1.setDepartement(dep1);
        departementRepository.save(subDepartement1);

        SubDepartement subDepartement2 = new SubDepartement();
        subDepartement2.setNomDepart("ITIT2");
        subDepartement2.setDescription("TestTest2");
        Departement dep2 = new Departement();
        dep2.setNomDepart("IT2");
        subDepartement1.setDepartement(dep2);
        departementRepository.save(subDepartement2);

        List<SubDepartement> result = departementService.retrieveAllSubDepartements();

        assertEquals(2, result.size());
    }

    @Test
    void testAddSubDepartement() {
        SubDepartement subDepartement = new SubDepartement();
        subDepartement.setNomDepart("Finance");
        subDepartement.setDescription("Service financiaire");
        Departement dep = new Departement();
        dep.setNomDepart("F");
        subDepartement.setDepartement(dep);

        SubDepartement result = departementService.addSubDepartement(subDepartement);

        assertNotNull(result.getIdSubDepart());
        assertEquals("Finance", result.getNomDepart());
    }

    @Test
    void testUpdateSubDepartement() {
        SubDepartement subDepartement = new SubDepartement();
        subDepartement.setNomDepart("Marketing");
        subDepartement.setDescription("Service marketing");
        Departement dep = new Departement();
        dep.setNomDepart("F");
        subDepartement.setDepartement(dep);
        SubDepartement savedDepartement = departementRepository.save(subDepartement);

        savedDepartement.setNomDepart("Sales");
        Departement dep1 = new Departement();
        dep1.setNomDepart("F2");
        savedDepartement.setDepartement(dep1);
        SubDepartement updatedResult = departementService.updateSubDepartement(savedDepartement);
        System.out.println(updatedResult.getDepartement().getNomDepart());
        assertEquals("Sales", updatedResult.getNomDepart());
        assertEquals("F2", updatedResult.getDepartement().getNomDepart());

    }

    @Test
    void testRetrieveSubDepartement() {
        SubDepartement subDepartement = new SubDepartement();
        subDepartement.setNomDepart("Operations");
        subDepartement.setDescription("Service Operations");
        Departement dep = new Departement();
        dep.setNomDepart("F");
        subDepartement.setDepartement(dep);
        SubDepartement savedDepartement = departementRepository.save(subDepartement);

        SubDepartement result = departementService.retrieveSubDepartement(savedDepartement.getIdSubDepart());

        assertNotNull(result);
        assertEquals("Operations", result.getNomDepart());
    }

    @Test
    void testDeleteSubDepartement() {
        SubDepartement subDepartement = new SubDepartement();
        subDepartement.setNomDepart("Logistics");
        subDepartement.setDescription("Service Logistics");
        Departement dep = new Departement();
        dep.setNomDepart("F");
        subDepartement.setDepartement(dep);

        SubDepartement savedDepartement = departementRepository.save(subDepartement);

        departementService.deleteSubDepartement(savedDepartement.getIdSubDepart());

        assertFalse(departementRepository.findById(savedDepartement.getIdSubDepart()).isPresent());
    }
    @Test
    void testAddSubDepartements() {
        SubDepartement subDepartement1 = new SubDepartement();
        subDepartement1.setNomDepart("HR");
        subDepartement1.setDescription("Human Resources");
        Departement dep1 = new Departement();
        dep1.setNomDepart("Human Capital");
        subDepartement1.setDepartement(dep1);
        SubDepartement subDepartement2 = new SubDepartement();
        subDepartement2.setNomDepart("IT Support");
        subDepartement2.setDescription("Support for IT");
        Departement dep2 = new Departement();
        dep2.setNomDepart("IT Department");
        subDepartement2.setDepartement(dep2);
        List<SubDepartement> subDepartements = Arrays.asList(subDepartement1, subDepartement2);
        List<SubDepartement> savedSubDepartements = departementService.addSubDepartements(subDepartements);
        assertEquals(2, savedSubDepartements.size());
        assertNotNull(savedSubDepartements.get(0).getIdSubDepart());
        assertNotNull(savedSubDepartements.get(1).getIdSubDepart());
    }
  /*  @Test
    void testCountSubDepartementsByDepartement() {
        // Create and save the Departement entity first
        Departement dep = new Departement();
        dep.setNomDepart("Finance");

        // Persist the Departement to make it managed
        dep = depart.addDepartement(dep);

        // Create SubDepartement instances and associate them with the persisted Departement
        SubDepartement subDepartement1 = new SubDepartement();
        subDepartement1.setNomDepart("Accounts");
        subDepartement1.setDescription("Accounts Department");
        subDepartement1.setDepartement(dep);

        SubDepartement subDepartement2 = new SubDepartement();
        subDepartement2.setNomDepart("Budgeting");
        subDepartement2.setDescription("Budgeting Department");
        subDepartement2.setDepartement(dep);

        // Save SubDepartement instances using the correct repository
        departementRepository.save(subDepartement1);
        departementRepository.save(subDepartement2);

        // Perform the count operation
        long count = departementService.countSubDepartementsByDepartement(dep.getIdDepart());

        // Assert the expected count
        assertEquals(2L, count);
    }*/


    @Test
    void testFindByDescriptionContaining() {
        SubDepartement subDepartement1 = new SubDepartement();
        subDepartement1.setNomDepart("Logistics");
        subDepartement1.setDescription("Handles logistics efficiently");

        SubDepartement subDepartement2 = new SubDepartement();
        subDepartement2.setNomDepart("Sales");
        subDepartement2.setDescription("Sales operations and management");

        departementRepository.save(subDepartement1);
        departementRepository.save(subDepartement2);

        List<SubDepartement> results = departementService.findByDescriptionContaining("logistics");

        assertEquals(1, results.size());
        assertEquals("Logistics", results.get(0).getNomDepart());
    }
    @Test
    void testFindByNomDepart() {
        SubDepartement subDepartement1 = new SubDepartement();
        subDepartement1.setNomDepart("Marketing");
        subDepartement1.setDescription("Marketing Department");
        Departement dep1 = new Departement();
        dep1.setNomDepart("Sales");
        subDepartement1.setDepartement(dep1);
        departementRepository.save(subDepartement1);

        SubDepartement subDepartement2 = new SubDepartement();
        subDepartement2.setNomDepart("Finance");
        subDepartement2.setDescription("Finance Department");
        Departement dep2 = new Departement();
        dep2.setNomDepart("Finance");
        subDepartement2.setDepartement(dep2);
        departementRepository.save(subDepartement2);

        List<SubDepartement> results = departementService.findByNomDepart("Sales");

        assertEquals(1, results.size());
        assertEquals("Marketing", results.get(0).getNomDepart());
    }

}
