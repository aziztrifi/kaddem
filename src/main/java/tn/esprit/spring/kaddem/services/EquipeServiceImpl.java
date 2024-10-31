package tn.esprit.spring.kaddem.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.IEquipeService;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class EquipeServiceImpl implements IEquipeService {
	private final EquipeRepository equipeRepository;

	public List<Equipe> retrieveAllEquipes() {
		return (List<Equipe>) equipeRepository.findAll();
	}

	public Equipe addEquipe(Equipe equipe) {
		return equipeRepository.save(equipe);
	}

	public void deleteEquipe(Integer id) {
		equipeRepository.findById(id).ifPresent(equipeRepository::delete);
	}

	public Equipe retrieveEquipe(Integer id) {
		return equipeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipe not found"));
	}

	public Equipe updateEquipe(Equipe equipe) {
		return equipeRepository.save(equipe);
	}

	public void evoluerEquipes() {
		List<Equipe> equipes = retrieveAllEquipes();
		equipes.forEach(this::processEquipeEvolution);
	}

	private void processEquipeEvolution(Equipe equipe) {
		if (equipe.getNiveau() == Niveau.JUNIOR || equipe.getNiveau() == Niveau.SENIOR) {
			int activeContractsCount = countActiveContracts(equipe);
			updateTeamLevel(equipe, activeContractsCount);
		}
	}

	private int countActiveContracts(Equipe equipe) {
		return equipe.getEtudiants().stream()
				.mapToInt(etudiant -> (int) etudiant.getContrats().stream()
						.filter(contrat -> !contrat.getArchive() && contrat.isActiveContract())
						.count())
				.sum();
	}

	private void updateTeamLevel(Equipe equipe, int activeContractsCount) {
		if (activeContractsCount >= 3) {
			switch (equipe.getNiveau()) {
				case JUNIOR:
					equipe.setNiveau(Niveau.SENIOR);
					break;
				case SENIOR:
					equipe.setNiveau(Niveau.EXPERT);
					break;
				default:
					break;
			}
			equipeRepository.save(equipe);
		}
	}
}
