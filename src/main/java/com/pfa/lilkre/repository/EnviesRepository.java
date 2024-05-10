package com.pfa.lilkre.repository;

import com.pfa.lilkre.entities.EnviesListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EnviesRepository extends JpaRepository<EnviesListEntity, Long>, JpaSpecificationExecutor<EnviesListEntity> {
    @Query(value = "select da from EnviesListEntity da where da.personne.id = :personneId and da.article.codeArticle = :articleId")
    public Optional<EnviesListEntity> findbyArticleIdandPersonneId(Long personneId, Long articleId);
}
