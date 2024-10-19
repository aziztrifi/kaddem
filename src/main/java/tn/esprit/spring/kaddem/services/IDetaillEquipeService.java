package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.DetailEquipe;

import java.util.List;


public interface IDetaillEquipeService {
    List<DetailEquipe> retrieveAllDetailEquipes();
    DetailEquipe retrieveDetailEquipe(Integer idDetailEquipe);
    DetailEquipe addDetailEquipe(DetailEquipe detailEquipe);
    DetailEquipe updateDetailEquipe(DetailEquipe detailEquipe);
    void deleteDetailEquipe(Integer idDetailEquipe);

    // Additional Services
    List<DetailEquipe> findByThematique(String thematique);
    int countBySalle(Integer salle);
}
