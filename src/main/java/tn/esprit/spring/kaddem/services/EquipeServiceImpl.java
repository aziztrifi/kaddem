package tn.esprit.spring.kaddem.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class EquipeServiceImpl implements IEquipeService {

	private final EquipeRepository equipeRepository;

	@Override
	public List<Equipe> retrieveAllEquipes() {
		return (List<Equipe>) equipeRepository.findAll();
	}

	@Override
	public Equipe addEquipe(Equipe equipe) {
		return equipeRepository.save(equipe);
	}

	@Override
	public void deleteEquipe(Integer id) {
		equipeRepository.findById(id).ifPresent(equipeRepository::delete);
	}

	@Override
	public Equipe retrieveEquipe(Integer id) {
		return equipeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Equipe not found"));
	}

	@Override
	public Equipe updateEquipe(Equipe equipe) {
		return equipeRepository.save(equipe);
	}

	/**
	 * This method promotes teams based on their active contracts.
	 */
	@Override
	public void evoluerEquipes() {
		List<Equipe> equipes = retrieveAllEquipes();
		equipes.forEach(this::processEquipeEvolution);
	}

	/**
	 * Checks the conditions for evolving the team's level and applies changes if applicable.
	 *
	 * @param equipe the team to check and possibly promote
	 */
	private void processEquipeEvolution(Equipe equipe) {
		if (equipe.getNiveau() == Niveau.JUNIOR || equipe.getNiveau() == Niveau.SENIOR) {
			int activeContractsCount = countActiveContracts(equipe);
			updateTeamLevel(equipe, activeContractsCount);
		}
	}

	/**
	 * Counts active contracts for each student in the team.
	 *
	 * @param equipe the team to calculate active contracts for
	 * @return the total number of active contracts
	 */
	private int countActiveContracts(Equipe equipe) {
		return equipe.getEtudiants().stream()
				.mapToInt(etudiant -> (int) etudiant.getContrats().stream()
						.filter(contrat -> !contrat.getArchive() && contrat.isActiveContract())
						.count())
				.sum();
	}

	/**
	 * Updates the team's level based on the count of active contracts.
	 *
	 * @param equipe              the team to update
	 * @param activeContractsCount the count of active contracts
	 */
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
