package com.pfa.lilkre.services.intf;

import com.pfa.lilkre.entities.CategorieEntity;
import com.pfa.lilkre.model.Article;
import com.pfa.lilkre.model.Categorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategorieService {
    public Page<Categorie> getAll(Pageable pageable);

    public Optional<Categorie> findById(Long id);

    public Categorie save(Categorie categorie);

    public Categorie update(Categorie categorie);

    public boolean delete(Long id);
}
