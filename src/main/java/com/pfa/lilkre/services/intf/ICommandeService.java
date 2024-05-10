package com.pfa.lilkre.services.intf;

import com.pfa.lilkre.entities.dto.AjouterCommande;
import com.pfa.lilkre.model.Commande;

import java.util.List;
import java.util.Optional;

public interface ICommandeService {
    public boolean delete(Long id);

    public void save(AjouterCommande ajouterCommande);

    public Optional<Commande> findById(Long id);

    public List<Commande> getAll();
}
