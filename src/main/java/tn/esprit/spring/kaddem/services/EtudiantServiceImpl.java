package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class EtudiantServiceImpl implements IEtudiantService {

	private final EtudiantRepository etudiantRepository;
	private final ContratRepository contratRepository;
	private final EquipeRepository equipeRepository;
	private final DepartementRepository departementRepository;

	@Autowired
	public EtudiantServiceImpl(EtudiantRepository etudiantRepository,
							   ContratRepository contratRepository,
							   EquipeRepository equipeRepository,
							   DepartementRepository departementRepository) {
		this.etudiantRepository = etudiantRepository;
		this.contratRepository = contratRepository;
		this.equipeRepository = equipeRepository;
		this.departementRepository = departementRepository;
	}

	@Override
	public List<Etudiant> retrieveAllEtudiants() {
		return (List<Etudiant>) etudiantRepository.findAll();
	}

	@Override
	public Etudiant addEtudiant(Etudiant e) {
		return etudiantRepository.save(e);
	}

	@Override
	public Etudiant updateEtudiant(Etudiant e) {
		return etudiantRepository.save(e);
	}

	@Override
	public Etudiant retrieveEtudiant(Integer idEtudiant) {
		return etudiantRepository.findById(idEtudiant)
				.orElseThrow(() -> new IllegalArgumentException("Etudiant not found with id: " + idEtudiant));
	}

	@Override
	public void removeEtudiant(Integer idEtudiant) {
		Etudiant e = retrieveEtudiant(idEtudiant);
		etudiantRepository.delete(e);
	}

	@Override
	public void assignEtudiantToDepartement(Integer etudiantId, Integer departementId) {
		Etudiant etudiant = etudiantRepository.findById(etudiantId)
				.orElseThrow(() -> new IllegalArgumentException("Etudiant not found with id: " + etudiantId));
		Departement departement = departementRepository.findById(departementId)
				.orElseThrow(() -> new IllegalArgumentException("Departement not found with id: " + departementId));

		etudiant.setDepartement(departement);
		etudiantRepository.save(etudiant);
	}

	@Transactional
	@Override
	public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe) {
		Contrat contrat = contratRepository.findById(idContrat)
				.orElseThrow(() -> new IllegalArgumentException("Contrat not found with id: " + idContrat));
		Equipe equipe = equipeRepository.findById(idEquipe)
				.orElseThrow(() -> new IllegalArgumentException("Equipe not found with id: " + idEquipe));

		contrat.setEtudiant(e);
		equipe.getEtudiants().add(e);

		etudiantRepository.save(e); // Persist the Etudiant entity
		contratRepository.save(contrat); // Persist the updated contract
		equipeRepository.save(equipe); // Persist the updated team

		return e;
	}

	@Override
	public List<Etudiant> getEtudiantsByDepartement(Integer idDepartement) {
		return etudiantRepository.findEtudiantsByDepartement_IdDepart(idDepartement);
	}
}
