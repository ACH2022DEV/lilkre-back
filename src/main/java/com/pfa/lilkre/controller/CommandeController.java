package com.pfa.lilkre.controller;

import com.pfa.lilkre.entities.dto.AjouterCommande;
import com.pfa.lilkre.model.Commande;
import com.pfa.lilkre.repository.CommandeRepository;
import com.pfa.lilkre.repository.PersonneRepository;
import com.pfa.lilkre.services.intf.ICommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/commande")
public class CommandeController {
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    ICommandeService iCommandeService;

    @GetMapping
    public List<Commande> list() {
        return iCommandeService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Commande> getCommande(@PathVariable Long id) {
        return iCommandeService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        iCommandeService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody AjouterCommande ajouterCommande) {
        iCommandeService.save(ajouterCommande);
    }

}
