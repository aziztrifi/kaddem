package tn.esprit.spring.kaddem.services;

import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.List;
import java.util.Set;


@Service
public class UniversiteServiceImpl implements IUniversiteService {

    private final UniversiteRepository universiteRepository;
    private final DepartementRepository departementRepository;

    public UniversiteServiceImpl(DepartementRepository departementRepository, UniversiteRepository universiteRepository) {
        this.departementRepository = departementRepository;
        this.universiteRepository = universiteRepository;
    }

    @Override
    public List<Universite> retrieveAllUniversites() {
        return (List<Universite>) universiteRepository.findAll();
    }

    @Override
    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversite(Integer idUniversite) {
        return universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new IllegalArgumentException("University not found with id: " + idUniversite));
    }

    @Override
    public void deleteUniversite(Integer idUniversite) {
        Universite universite = retrieveUniversite(idUniversite);
        universiteRepository.delete(universite);
    }

    @Override
    public void assignUniversiteToDepartement(Integer idUniversite, Integer idDepartement) {
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new IllegalArgumentException("Universite not found with id: " + idUniversite));
        Departement departement = departementRepository.findById(idDepartement)
                .orElseThrow(() -> new IllegalArgumentException("Departement not found with id: " + idDepartement));

        universite.getDepartements().add(departement);
        universiteRepository.save(universite);
    }

    @Override
    public Set<Departement> retrieveDepartementsByUniversite(Integer idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new IllegalArgumentException("Universite not found with id: " + idUniversite));

        return universite.getDepartements();
    }
}
