package com.pfa.lilkre.services.impl;

import com.pfa.lilkre.entities.ArticleEntity;
import com.pfa.lilkre.entities.PanierEntity;
import com.pfa.lilkre.entities.PersonneEntity;
import com.pfa.lilkre.entities.dto.AjoutPanierDto;
import com.pfa.lilkre.entities.dto.CreateUpdatePanierDto;
import com.pfa.lilkre.mappers.PanierMapper;
import com.pfa.lilkre.model.Panier;
import com.pfa.lilkre.repository.ArticleRepository;
import com.pfa.lilkre.repository.PanierRepository;
import com.pfa.lilkre.repository.PersonneRepository;
import com.pfa.lilkre.services.intf.IPanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PanierServiceImp implements IPanierService {
    @Autowired
    PanierRepository panierRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleServiceImpl articleService;
    @Autowired
    PersonneServiceImpl personneService;

    @Autowired
    PersonneRepository personneRepository;

    public List<Panier> getAll() {

        return PanierMapper.INSTANCE.mapToModels(panierRepository.findAll());
    }

    @Override
    public Optional<Panier> findById(Long id) {
        return panierRepository.findById(id).map(PanierMapper.INSTANCE::mapToModel);
    }


    //IL ESTobligatoire d'utiliser authentification (token )
    // la premiere Methode
    @Override
    public void save(CreateUpdatePanierDto panier) {
        panierRepository.findbyPersonneId(panier.getIdPersonne()).forEach(panToDelete -> {
            panierRepository.delete(panToDelete);
        });

        panier.getPaniers().forEach(pan -> {
            PanierEntity panierEntityToSave = new PanierEntity();
            // il faut que l'article soit présent
            articleRepository.findById(pan.getCodeArticle())
                    .map(art -> {
                        panierEntityToSave.setArticle(art);
                        return panierEntityToSave;
                    })
                    .orElseThrow(() -> new RuntimeException("Artcile inexistant"));
            panierEntityToSave.setQuantity(pan.getQuantity());
            panierEntityToSave.setDate(LocalDateTime.now());
            // il faut que la personne soit présente
            personneRepository.findById(panier.getIdPersonne()).map(per -> {
                panierEntityToSave.setPersonne(per);
                return panierEntityToSave;
            }).orElseThrow(() -> new RuntimeException("Personnes inexistante"));
            panierRepository.save(panierEntityToSave);
        });
    }


    @Override
    public void ajout(AjoutPanierDto panier) {
        Optional<PanierEntity> ArticlePersonneExiste = panierRepository.findbyArticleIdandPersonneId(
                panier.getIdPersonne(), panier.getPaniers().getCodeArticle());
        Optional<ArticleEntity> panier_article = articleRepository.findById(panier.getPaniers().getCodeArticle());
        //System.out.println("result  " + panierRepository.sumQuantitiesByArticleId(panier.getPaniers().getCodeArticle()));
        int quantityOfArticle = panierRepository.sumQuantitiesByArticleId(panier.getPaniers().getCodeArticle());
        int quantiteDisponible = panier_article.get().getQuantite();
        System.out.println("quantityOfArticle  " + quantityOfArticle);
        //faire une condition pour vérifier si le quantité ne dépasse pas le quantité disponible dans le stock
        if (quantityOfArticle < quantiteDisponible) {
            if (ArticlePersonneExiste.isEmpty()) {
                PanierEntity panierEntityToSave = new PanierEntity();
                if (panier.getPaniers() != null) {
                    panierEntityToSave.setQuantity(panier.getPaniers().getQuantity());
                    panierEntityToSave.setDate(LocalDateTime.now());
                    Optional<PersonneEntity> personne = personneRepository.findById(panier.getIdPersonne());
                    panierEntityToSave.setPersonne(personne.get());
                    Optional<ArticleEntity> article = articleRepository.findById(panier.getPaniers().getCodeArticle());
                    panierEntityToSave.setArticle(article.get());
                    panierRepository.save(panierEntityToSave);
                }
            } else {
                int quantity = ArticlePersonneExiste.get().getQuantity();
                ArticlePersonneExiste.get().setQuantity(quantity + 1);
            }
        } else {
            System.out.println("Rupture de stock: ");
        }
    }


    //ajouter une autre méthode pour duminuer le quantité de panier_article
    public void decrementeQuantity_Article(AjoutPanierDto panier) {
        Optional<PanierEntity> ArticlePersonneExiste = panierRepository.findbyArticleIdandPersonneId(
                panier.getIdPersonne(), panier.getPaniers().getCodeArticle());
        int quantity = ArticlePersonneExiste.get().getQuantity();
        if (quantity > 1) {
            ArticlePersonneExiste.get().setQuantity(quantity - 1);
        } else {
            System.out.println("you can't decremente this!");
        }

    }

    public ResponseEntity<?> VerifierDisponibility_article(AjoutPanierDto panier) {
        Optional<PanierEntity> ArticlePersonneExiste = panierRepository.findbyArticleIdandPersonneId(
                panier.getIdPersonne(), panier.getPaniers().getCodeArticle());
        Optional<ArticleEntity> panier_article = articleRepository.findById(panier.getPaniers().getCodeArticle());
        int quantityOfArticle = panierRepository.sumQuantitiesByArticleId(panier.getPaniers().getCodeArticle());
        int quantiteDisponible = panier_article.get().getQuantite();
        System.out.println("quantityOfArticle  " + quantityOfArticle);
        //faire une condition pour vérifier si le quantité ne dépasse pas le quantité disponible dans le stock
        if (quantityOfArticle < quantiteDisponible) {
            System.out.println("Disponible");
            return ResponseEntity.ok().body("{\"message\": \"Disponible\"}");
        } else {
            System.out.println("Non Disponible");
            return ResponseEntity.ok().body("{\"message\": \"Non Disponible\"}");
        }
    }


    //la première code//
    public boolean delete(Long id) {
        Optional<PanierEntity> a = panierRepository.findById(id);
        if (a.isPresent()) {
            panierRepository.deleteById(id);
            return true;
        }
        return false;
    }
   /* public boolean delete(long codeArticle){
        Optional<ArticleEntity> article=articleRepository.findById(codeArticle);
        if (article.isPresent()) {
            panierRepository.findbyArticleId(article.get().getCodeArticle());
            return true;
        }
        return false;
    }*/

}
