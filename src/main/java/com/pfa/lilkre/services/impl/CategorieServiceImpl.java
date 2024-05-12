package com.pfa.lilkre.services.impl;

import com.pfa.lilkre.entities.ArticleEntity;
import com.pfa.lilkre.entities.CategorieEntity;
import com.pfa.lilkre.repository.CategorieRepository;
import com.pfa.lilkre.services.intf.ICategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class CategorieServiceImpl implements ICategorieService {
    @Autowired
    CategorieRepository repository;







    @Override
    public Page<CategorieEntity> getAll(Pageable pageable) {

        Page<CategorieEntity> categorieEntityPage = repository.findAll(pageable);
        List<CategorieEntity> categorieEntityList = categorieEntityPage.stream().toList();
        List<CategorieEntity> CategorieListList =
                categorieEntityList.stream().toList();


        return new PageImpl<>(CategorieListList, pageable, categorieEntityPage.getTotalElements());
    }

    public Optional<CategorieEntity> findById(Long id) {
        return repository.findById(id);
    }


    public CategorieEntity save(CategorieEntity categorie) {
        return repository.save(categorie);
    }

    public CategorieEntity update(CategorieEntity categorie) {
        return repository.save(categorie);
    }


    public boolean delete(Long id) {
        Optional<CategorieEntity> ar = repository.findById(id);
        if (ar.isPresent()) {
            repository.deleteById(id);
        }
        return true;
    }

}
