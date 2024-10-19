import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.spring.kaddem.entities.DetailEquipe;
import tn.esprit.spring.kaddem.repositories.DetailEquipeRepository;
import tn.esprit.spring.kaddem.services.DetailEquipeServiceImpl;

public class DetailEquipeServiceImplMockTest {

    @InjectMocks
    private DetailEquipeServiceImpl detailEquipeService;

    @Mock
    private DetailEquipeRepository detailEquipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllDetailEquipes() {
        DetailEquipe detail1 = new DetailEquipe(1, 101, "Thematique 1");
        DetailEquipe detail2 = new DetailEquipe(2, 102, "Thematique 2");
        List<DetailEquipe> details = Arrays.asList(detail1, detail2);

        when(detailEquipeRepository.findAll()).thenReturn(details);

        List<DetailEquipe> result = detailEquipeService.retrieveAllDetailEquipes();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getIdDetailEquipe());
        assertEquals(102, result.get(1).getSalle());
        verify(detailEquipeRepository, times(1)).findAll();
    }

    @Test
    void testAddDetailEquipe() {
        DetailEquipe detail = new DetailEquipe(101, "Thematique Test");

        when(detailEquipeRepository.save(any(DetailEquipe.class))).thenReturn(detail);

        DetailEquipe result = detailEquipeService.addDetailEquipe(detail);

        assertEquals(101, result.getSalle());
        assertEquals("Thematique Test", result.getThematique());
        verify(detailEquipeRepository, times(1)).save(detail);
    }

    @Test
    void testUpdateDetailEquipe() {
        DetailEquipe detail = new DetailEquipe(1, 202, "Thematique Updated");

        when(detailEquipeRepository.save(any(DetailEquipe.class))).thenReturn(detail);

        DetailEquipe result = detailEquipeService.updateDetailEquipe(detail);

        assertEquals(202, result.getSalle());
        assertEquals("Thematique Updated", result.getThematique());
        verify(detailEquipeRepository, times(1)).save(detail);
    }

    @Test
    void testRetrieveDetailEquipe() {
        Integer detailId = 1;
        DetailEquipe detail = new DetailEquipe(detailId, 103, "Thematique Test");

        when(detailEquipeRepository.findById(detailId)).thenReturn(Optional.of(detail));

        DetailEquipe result = detailEquipeService.retrieveDetailEquipe(detailId);

        assertEquals(103, result.getSalle());
        assertEquals("Thematique Test", result.getThematique());
        verify(detailEquipeRepository, times(1)).findById(detailId);
    }

    @Test
    void testDeleteDetailEquipe() {
        Integer detailId = 1;
        DetailEquipe detail = new DetailEquipe(detailId, 104, "Thematique to Delete");

        when(detailEquipeRepository.findById(detailId)).thenReturn(Optional.of(detail));

        detailEquipeService.deleteDetailEquipe(detailId);

        verify(detailEquipeRepository, times(1)).deleteById(detailId);
    }

    @Test
    void testFindByThematique() {
        DetailEquipe detail1 = new DetailEquipe(1, 105, "Thematique 1");
        DetailEquipe detail2 = new DetailEquipe(2, 106, "Thematique 1");
        List<DetailEquipe> details = Arrays.asList(detail1, detail2);

        when(detailEquipeRepository.findAll()).thenReturn(details);

        List<DetailEquipe> result = detailEquipeService.findByThematique("Thematique 1");

        assertEquals(2, result.size());
        verify(detailEquipeRepository, times(1)).findAll();
    }

    @Test
    void testCountBySalle() {
        DetailEquipe detail1 = new DetailEquipe(1, 107, "Thematique 1");
        DetailEquipe detail2 = new DetailEquipe(2, 107, "Thematique 2");
        List<DetailEquipe> details = Arrays.asList(detail1, detail2);

        when(detailEquipeRepository.findAll()).thenReturn(details);

        int result = detailEquipeService.countBySalle(107);

        assertEquals(2, result);
        verify(detailEquipeRepository, times(1)).findAll();
    }
}

