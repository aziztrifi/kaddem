package tn.esprit.spring.kaddem.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.kaddem.entities.SubDepartement;

import java.util.List;

@Repository
public interface SubDepartementRepository extends CrudRepository<SubDepartement,Integer> {

    @Query("select s from SubDepartement s where s.departement.nomDepart=:nomDepart")
    List<SubDepartement> findByNomDepart(String nomDepart);

    @Query("select count (*) from SubDepartement s where s.departement.idDepart=:departementId")
    long countByDepartementId(long departementId);

    List<SubDepartement> findByDescriptionContaining(String keyword);


}
