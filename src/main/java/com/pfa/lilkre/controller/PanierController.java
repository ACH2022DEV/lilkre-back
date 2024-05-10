package com.pfa.lilkre.controller;


import com.pfa.lilkre.entities.PanierEntity;
import com.pfa.lilkre.entities.dto.AjoutPanierDto;
import com.pfa.lilkre.entities.dto.CreateUpdatePanierDto;
import com.pfa.lilkre.model.Panier;
import com.pfa.lilkre.repository.PanierRepository;
import com.pfa.lilkre.services.intf.IPanierService;
import com.pfa.lilkre.services.intf.IPersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/panier")
public class PanierController {
    @Autowired
    IPanierService iPanierService;
    @Autowired
    IPersonneService iPersonneService;
    @Autowired
    PanierRepository panierRepository;

    @GetMapping
    public List<Panier> list() {
        return iPanierService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Panier> getPanier(@PathVariable Long id) {
        return iPanierService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody CreateUpdatePanierDto panier) {

        iPanierService.save(panier);
    }

    @PutMapping
    public void ajout(@RequestBody AjoutPanierDto panier) {
// rajouter la fonctionalité ajout d'un panier

        iPanierService.ajout(panier);
    }


    @GetMapping("/getPanier_Client/{id}")
    public ResponseEntity<List<PanierEntity>> getPanierByClientId(@PathVariable Long id) {
        List<PanierEntity> panier = panierRepository.findbyPersonneId(id);
        // List<ArticleEntity> articles = panier.stream().map(PanierEntity::getArticle).collect(Collectors.toList());
        return ResponseEntity.ok(panier);
    }

    @PostMapping("/dimunuerQuantity")
    public void dimunuerQuantity(@RequestBody AjoutPanierDto panier) {
        //panierRepository.findbyPersonneId(id).forEach(panierRepository::delete);
        iPanierService.decrementeQuantity_Article(panier);
    }

    @PostMapping("/VerifierDisponibility_article")
    public ResponseEntity<?> VerifierDisponibility_article(@RequestBody AjoutPanierDto panier) {
        return iPanierService.VerifierDisponibility_article(panier);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        //panierRepository.findbyPersonneId(id).forEach(panierRepository::delete);
        iPanierService.delete(id);
    }

}
