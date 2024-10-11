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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubDepartementServiceImplTest {
    @Autowired
    private SubDepartementRepository departementRepository;

    @Autowired
    private SubDepartementServiceImpl departementService;

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

}
