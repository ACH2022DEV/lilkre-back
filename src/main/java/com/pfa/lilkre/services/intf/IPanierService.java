package com.pfa.lilkre.services.intf;

import com.pfa.lilkre.entities.dto.AjoutPanierDto;
import com.pfa.lilkre.entities.dto.CreateUpdatePanierDto;
import com.pfa.lilkre.model.Panier;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IPanierService {
    public List<Panier> getAll();

    public Optional<Panier> findById(Long id);

    public void save(CreateUpdatePanierDto panier);

    //public Panier update(Panier panier);
    public boolean delete(Long id);

    public void ajout(AjoutPanierDto panier);

    //public boolean delete( long codeArticle);
    public void decrementeQuantity_Article(AjoutPanierDto panier);

    public ResponseEntity<?> VerifierDisponibility_article(AjoutPanierDto panier);
}
