package tn.esprit.spring.kaddem.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.entities.DetailEquipe;
import tn.esprit.spring.kaddem.repositories.DetailEquipeRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j

@Service
public class DetailEquipeServiceImpl implements IDetaillEquipeService{

    @Autowired
    private DetailEquipeRepository detailEquipeRepository;

    @Override
    public List<DetailEquipe> retrieveAllDetailEquipes() {
        return (List<DetailEquipe>) detailEquipeRepository.findAll();
    }

    @Override
    public DetailEquipe retrieveDetailEquipe(Integer idDetailEquipe) {
        return detailEquipeRepository.findById(idDetailEquipe).orElse(null);
    }

    @Override
    public DetailEquipe addDetailEquipe(DetailEquipe detailEquipe) {
        return detailEquipeRepository.save(detailEquipe);
    }

    @Override
    public DetailEquipe updateDetailEquipe(DetailEquipe detailEquipe) {
        return detailEquipeRepository.save(detailEquipe);
    }

    @Override
    public void deleteDetailEquipe(Integer idDetailEquipe) {
        detailEquipeRepository.deleteById(idDetailEquipe);
    }

    // Additional Services
    @Override
    public List<DetailEquipe> findByThematique(String thematique) {
        return StreamSupport.stream(detailEquipeRepository.findAll().spliterator(), false)
                .filter(de -> de.getThematique().equals(thematique))
                .collect(Collectors.toList());  // Use Collectors.toList() to collect the stream into a List
    }

    @Override
    public int countBySalle(Integer salle) {
        return (int) StreamSupport.stream(detailEquipeRepository.findAll().spliterator(), false)
                .filter(de -> de.getSalle().equals(salle))
                .count();
    }

}
