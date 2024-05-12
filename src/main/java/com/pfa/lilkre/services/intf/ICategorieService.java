package com.pfa.lilkre.services.intf;

import com.pfa.lilkre.entities.CategorieEntity;
import com.pfa.lilkre.model.Article;
import com.pfa.lilkre.model.Categorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategorieService {
    public Page<CategorieEntity> getAll(Pageable pageable);

    public Optional<CategorieEntity> findById(Long id);

    public CategorieEntity save(CategorieEntity categorie);

    public CategorieEntity update(CategorieEntity categorie);

    public boolean delete(Long id);
}
