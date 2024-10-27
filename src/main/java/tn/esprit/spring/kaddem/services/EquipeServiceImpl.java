package tn.esprit.spring.kaddem.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class EquipeServiceImpl implements IEquipeService {
	private final EquipeRepository equipeRepository;

	public List<Equipe> retrieveAllEquipes() {
		return (List<Equipe>) equipeRepository.findAll();
	}

	public Equipe addEquipe(Equipe e) {
		return equipeRepository.save(e);
	}

	public void deleteEquipe(Integer idEquipe) {
		Optional<Equipe> equipe = equipeRepository.findById(idEquipe);
		equipe.ifPresent(equipeRepository::delete);
	}

	public Equipe retrieveEquipe(Integer equipeId) {
		return equipeRepository.findById(equipeId).orElse(null);  // ou gérer avec une exception si null
	}

	public Equipe updateEquipe(Equipe e) {
		return equipeRepository.save(e);
	}

	public void evoluerEquipes() {
		List<Equipe> equipes = (List<Equipe>) equipeRepository.findAll();
		for (Equipe equipe : equipes) {
			if (isEligibleForEvolution(equipe)) {
				incrementEquipeLevel(equipe);
			}
		}
	}

	// Méthode pour vérifier si une équipe est éligible pour évoluer
	private boolean isEligibleForEvolution(Equipe equipe) {
		if (!(equipe.getNiveau() == Niveau.JUNIOR || equipe.getNiveau() == Niveau.SENIOR)) {
			return false;
		}

		int nbEtudiantsAvecContratsActifs = 0;
		for (Etudiant etudiant : equipe.getEtudiants()) {
			if (hasActiveContractForOverOneYear(etudiant)) {
				nbEtudiantsAvecContratsActifs++;
				if (nbEtudiantsAvecContratsActifs >= 3) {
					return true;
				}
			}
		}
		return false;
	}

	// Méthode pour vérifier si un étudiant a un contrat actif de plus d'un an
	private boolean hasActiveContractForOverOneYear(Etudiant etudiant) {
		Date dateSysteme = new Date();
		for (Contrat contrat : etudiant.getContrats()) {
			long differenceInTime = dateSysteme.getTime() - contrat.getDateFinContrat().getTime();
			long differenceInYears = (differenceInTime / (1000L * 60 * 60 * 24 * 365));
			if (!contrat.getArchive() && differenceInYears > 1) {
				return true;
			}
		}
		return false;
	}

	// Méthode pour faire évoluer le niveau d'une équipe
	private void incrementEquipeLevel(Equipe equipe) {
		if (equipe.getNiveau() == Niveau.JUNIOR) {
			equipe.setNiveau(Niveau.SENIOR);
		} else if (equipe.getNiveau() == Niveau.SENIOR) {
			equipe.setNiveau(Niveau.EXPERT);
		}
		equipeRepository.save(equipe);
	}
}
