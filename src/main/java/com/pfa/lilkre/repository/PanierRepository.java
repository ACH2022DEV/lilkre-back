package com.pfa.lilkre.repository;

import com.pfa.lilkre.entities.PanierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PanierRepository extends JpaRepository<PanierEntity, Long>, JpaSpecificationExecutor<PanierEntity> {
    //public List<PanierEntity> findByClient(PersonneEntity client);
    //public PanierEntity findByClientAndArticle(PersonneEntity client, ArticleEntity article);
    @Query(value = "select da from PanierEntity da where da.personne.id = :personneId and da.article.codeArticle = :articleId")
    public Optional<PanierEntity> findbyArticleIdandPersonneId(Long personneId, Long articleId);

    @Query(value = "select pan from PanierEntity pan where pan.personne.id = :personneId ")
    public List<PanierEntity> findbyPersonneId(Long personneId);

    //nouvelle Modification
    @Query(value = "select pan from PanierEntity pan where pan.article.codeArticle = :ArticleId ")
    public List<PanierEntity> findbyArticleId(Long ArticleId);

    //nouveau ligne
    //calculer le quantité des produits
    @Query("SELECT COALESCE(SUM(pan.quantity), 0) FROM PanierEntity pan WHERE pan.article.codeArticle = :articleId")
    int sumQuantitiesByArticleId(@Param("articleId") Long articleId);


}
