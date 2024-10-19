package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.DetailEquipe;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;
import tn.esprit.spring.kaddem.services.IDetaillEquipeService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/detailEquipe")
public class DetailEquipeController {

     IDetaillEquipeService detailEquipeService;

    @GetMapping
    public List<DetailEquipe> getAllDetailEquipes() {
        return detailEquipeService.retrieveAllDetailEquipes();
    }

    @GetMapping("/{id}")
    public DetailEquipe getDetailEquipeById(@PathVariable("id") Integer id) {
        return detailEquipeService.retrieveDetailEquipe(id);
    }

    @PostMapping
    public DetailEquipe addDetailEquipe(@RequestBody DetailEquipe detailEquipe) {
        return detailEquipeService.addDetailEquipe(detailEquipe);
    }

    @PutMapping
    public DetailEquipe updateDetailEquipe(@RequestBody DetailEquipe detailEquipe) {
        return detailEquipeService.updateDetailEquipe(detailEquipe);
    }

    @DeleteMapping("/{id}")
    public void deleteDetailEquipe(@PathVariable("id") Integer id) {
        detailEquipeService.deleteDetailEquipe(id);
    }

    // Additional Services
    @GetMapping("/thematique/{thematique}")
    public List<DetailEquipe> findByThematique(@PathVariable("thematique") String thematique) {
        return detailEquipeService.findByThematique(thematique);
    }

    @GetMapping("/count/{salle}")
    public int countBySalle(@PathVariable("salle") Integer salle) {
        return detailEquipeService.countBySalle(salle);
    }
}
