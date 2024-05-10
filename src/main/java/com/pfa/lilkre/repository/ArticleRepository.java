package com.pfa.lilkre.repository;

import com.pfa.lilkre.entities.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long>, JpaSpecificationExecutor<ArticleEntity> {


    //Optional<ArticleEntity> findByCodeArticle(Long codeArticle);


    @Query("select art from ArticleEntity art where " + "CONCAT( art.description,art.codeArticle,art.paysOrigine,art.prix,art.tva,art.remise,art.quantite) " + "LIKE CONCAT( '%',:keyword,'%')")
    public Page<ArticleEntity> findBySearch(Pageable pageable, String keyword);


  /*  @Query("select art from ArticleEntity art where  art.description Like(:keyword)")
    public Page<ArticleEntity> findByDescription(Pageable pageable,String keyword);
    */
  /*  @Query(value = "select i from ArticleEntity i  where  i.images.id in (articleId)")
    public List<ArticleEntity> findbyArticleId1(Long articleId);*/

    //    @Query("select art from ArticleEntity art where art.avis.etoile =(select avis from AvisEntity avis where avis.etoile= :NbAvis)")
    //@Query("SELECT a FROM ArticleEntity a JOIN a.avis av WHERE av.NbAvis = :NbAvis")
    public Page<ArticleEntity> findByAvis(Pageable pageable, Integer NbAvis);

    @Query("select art from ArticleEntity art where  art.remise <=:remise")
    public Page<ArticleEntity> findByremise(Pageable pageable, Integer remise);


}
