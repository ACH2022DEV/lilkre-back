package com.pfa.lilkre.mappers;

import com.pfa.lilkre.entities.ArticleEntity;
import com.pfa.lilkre.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ImagesMapper.class, PanierMapper.class, AvisMapper.class, CommandeMapper.class})
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    Article mapToModel(ArticleEntity article);

    ArticleEntity mapToEntity(Article article);

    List<ArticleEntity> mapToEntities(List<Article> articles);
    //@Mapping(source="article", target = "article", ignore = true)//par moi

    List<Article> mapToModels(List<ArticleEntity> articles);
}
