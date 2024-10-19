package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.entities.SubDepartement;
import tn.esprit.spring.kaddem.repositories.SubDepartementRepository;

import java.util.List;

@Service
public class SubDepartementServiceImpl {

    @Autowired
    SubDepartementRepository subDepartementRepository;
    public List<SubDepartement> retrieveAllSubDepartements(){
        return (List<SubDepartement>) subDepartementRepository.findAll();
    }

    public SubDepartement addSubDepartement (SubDepartement d){
        return subDepartementRepository.save(d);
    }

    public   SubDepartement updateSubDepartement (SubDepartement d){
        return subDepartementRepository.save(d);
    }

    public  SubDepartement retrieveSubDepartement (Integer idDepart){
        return subDepartementRepository.findById(idDepart).get();
    }
    public  void deleteSubDepartement(Integer idDepartement){
        SubDepartement d=retrieveSubDepartement(idDepartement);
        subDepartementRepository.delete(d);
    }

    public List<SubDepartement> findByNomDepart(String nomDepart) {
        return subDepartementRepository.findByNomDepart(nomDepart);
    }
    public List<SubDepartement> addSubDepartements(List<SubDepartement> subDepartements) {
        return (List<SubDepartement>) subDepartementRepository.saveAll(subDepartements);
    }
    public long countSubDepartementsByDepartement(long departementId) {
        return subDepartementRepository.countByDepartementId(departementId);
    }

    public List<SubDepartement> findByDescriptionContaining(String keyword) {
        return subDepartementRepository.findByDescriptionContaining(keyword);
    }


}
