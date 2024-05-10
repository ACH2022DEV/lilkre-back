package com.pfa.lilkre.services.impl;

import com.pfa.lilkre.entities.ArticleEntity;
import com.pfa.lilkre.entities.EnviesListEntity;
import com.pfa.lilkre.entities.PersonneEntity;
import com.pfa.lilkre.entities.dto.CreatedEnviesList;
import com.pfa.lilkre.repository.ArticleRepository;
import com.pfa.lilkre.repository.EnviesRepository;
import com.pfa.lilkre.repository.PersonneRepository;
import com.pfa.lilkre.services.intf.IEnviesService;
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
public class EnviesServiceImpl implements IEnviesService {
    @Autowired
    EnviesRepository enviesRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    PersonneRepository personneRepository;

    @Override
    public Page<EnviesListEntity> getAll(Pageable pageable) {
        Page<EnviesListEntity> enviesListEntityPage = enviesRepository.findAll(pageable);
        List<EnviesListEntity> enviesListEntityList = enviesListEntityPage.stream().toList();
        List<EnviesListEntity> enviesEntityList = enviesListEntityList.stream().toList();
        return new PageImpl<>(enviesEntityList, pageable, enviesListEntityPage.getTotalElements());
    }


    @Override
    public Optional<EnviesListEntity> findById(Long id) {
        return enviesRepository.findById(id);
    }

    public void save(CreatedEnviesList enviesList) {
        Optional<EnviesListEntity> ArticlePersonneExiste = enviesRepository.findbyArticleIdandPersonneId(
                enviesList.getIdPersonne(), enviesList.getCodeArticle());
        Optional<PersonneEntity> personne = personneRepository.findById(enviesList.getIdPersonne());
        Optional<ArticleEntity> article = articleRepository.findById(enviesList.getCodeArticle());
        if (ArticlePersonneExiste.isEmpty()) {
            EnviesListEntity enviesListEntity = new EnviesListEntity();
            enviesListEntity.setArticle(article.get());
            enviesListEntity.setPersonne(personne.get());
            enviesRepository.save(enviesListEntity);
        } else {
            System.out.println("déja existe");
        }
    }

    public boolean delete(Long id) {
        Optional<EnviesListEntity> a = enviesRepository.findById(id);
        if (a.isPresent()) {
            enviesRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
