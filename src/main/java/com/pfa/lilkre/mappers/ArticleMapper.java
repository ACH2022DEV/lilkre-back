package com.pfa.lilkre.mappers;

import com.pfa.lilkre.entities.ArticleEntity;
import com.pfa.lilkre.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ImagesMapper.class, PanierMapper.class, AvisMapper.class, CommandeMapper.class,CategorieMapper.class})
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    Article mapToModel(ArticleEntity article);

    ArticleEntity mapToEntity(Article article);
    @Mapping(source="categories", target = "articles", ignore = true)
    List<ArticleEntity> mapToEntities(List<Article> articles);
    @Mapping(source="articles", target = "articles", ignore = true)

    List<Article> mapToModels(List<ArticleEntity> articles);
}
