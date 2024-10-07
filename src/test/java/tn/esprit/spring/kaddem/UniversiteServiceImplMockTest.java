package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;
import tn.esprit.spring.kaddem.services.UniversiteServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UniversiteServiceImplMockTest {

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllUniversites() {
        Universite univ1 = new Universite();
        univ1.setIdUniv(1);
        univ1.setNomUniv("Université de Tunis");

        Universite univ2 = new Universite();
        univ2.setIdUniv(2);
        univ2.setNomUniv("Université de Sfax");

        when(universiteRepository.findAll()).thenReturn(Arrays.asList(univ1, univ2));

        List<Universite> result = universiteService.retrieveAllUniversites();

        assertEquals(2, result.size());
        assertEquals("Université de Tunis", result.get(0).getNomUniv());
        assertEquals("Université de Sfax", result.get(1).getNomUniv());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testAddUniversite() {
        Universite universite = new Universite();
        universite.setNomUniv("Université de Carthage");

        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.addUniversite(universite);

        assertNotNull(result);
        assertEquals("Université de Carthage", result.getNomUniv());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testUpdateUniversite() {
        Universite universite = new Universite();
        universite.setIdUniv(1);
        universite.setNomUniv("Université de Monastir");

        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.updateUniversite(universite);

        assertNotNull(result);
        assertEquals("Université de Monastir", result.getNomUniv());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveUniversite() {
        Integer id = 1;
        Universite universite = new Universite();
        universite.setIdUniv(id);
        universite.setNomUniv("Université de Gabès");

        when(universiteRepository.findById(id)).thenReturn(Optional.of(universite));

        Universite result = universiteService.retrieveUniversite(id);

        assertNotNull(result);
        assertEquals("Université de Gabès", result.getNomUniv());
        verify(universiteRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteUniversite() {
        Integer id = 1;
        Universite universite = new Universite();
        universite.setIdUniv(id);
        universite.setNomUniv("Université de Gafsa");

        when(universiteRepository.findById(id)).thenReturn(Optional.of(universite));

        universiteService.deleteUniversite(id);

        verify(universiteRepository, times(1)).delete(universite);
    }
}

