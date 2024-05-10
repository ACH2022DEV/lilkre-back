package com.pfa.lilkre.services.impl;

import com.pfa.lilkre.entities.CommandeEntity;
import com.pfa.lilkre.entities.dto.AjouterCommande;
import com.pfa.lilkre.mappers.CommandeMapper;
import com.pfa.lilkre.model.Commande;
import com.pfa.lilkre.repository.ArticleRepository;
import com.pfa.lilkre.repository.CommandeRepository;
import com.pfa.lilkre.repository.PersonneRepository;
import com.pfa.lilkre.services.intf.ICommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommandeServiceImpl implements ICommandeService {
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    ArticleRepository articleRepository;


    public List<Commande> getAll() {
        return CommandeMapper.INSTANCE.mapToModels(commandeRepository.findAll());
    }

    public Optional<Commande> findById(Long id) {
        return commandeRepository.findById(id).map(CommandeMapper.INSTANCE::mapToModel);
    }

    @Override
    public void save(AjouterCommande ajouterCommande) {


        if (ajouterCommande.getCommande() != null) {
            ajouterCommande.getCommande().forEach(commande -> {
                CommandeEntity commandeEntityToSave = new CommandeEntity();

                articleRepository.findById(commande.getCodeArticle())
                        .map(art -> {
                            commandeEntityToSave.setArticle(art);
                            return commandeEntityToSave;
                        })
                        .orElseThrow(() -> new RuntimeException("Artcile inexistant"));
                commandeEntityToSave.setDateCommande(LocalDateTime.now());
                commandeEntityToSave.setLepayment(ajouterCommande.getLepayment());
                commandeEntityToSave.setTel(ajouterCommande.getTel());
                commandeEntityToSave.setVille(ajouterCommande.getVille());
                commandeEntityToSave.setQuantity(ajouterCommande.getQuantity());
                commandeEntityToSave.setDateLivraison(ajouterCommande.getDateLivraison());
                commandeEntityToSave.setAdress(ajouterCommande.getAdress());
                commandeEntityToSave.setCodePostal(ajouterCommande.getCodePostal());
                personneRepository.findById(ajouterCommande.getIdPersonne()).map(per -> {
                    commandeEntityToSave.setPersonne(per);
                    return commandeEntityToSave;
                });
                commandeRepository.save(commandeEntityToSave);
            });
        }
    }

    public boolean delete(Long id) {

        Optional<CommandeEntity> commande = commandeRepository.findById(id);
        if (commande.isPresent()) {
            commandeRepository.deleteById(id);
        }
        return true;
    }
}

