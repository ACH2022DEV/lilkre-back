

package com.pfa.lilkre.mappers;

import com.pfa.lilkre.entities.ArticleEntity;
import com.pfa.lilkre.entities.CategorieEntity;
import com.pfa.lilkre.model.Article;
import com.pfa.lilkre.model.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(uses = ArticleMapper.class)
//CategorieMapper
public interface CategorieMapper {
    CategorieMapper INSTANCE = Mappers.getMapper(CategorieMapper.class);
    ArticleMapper articleMapper = ArticleMapper.INSTANCE;
    @Mapping(source="articles", target = "articles", ignore = true)
    Categorie mapToModel(CategorieEntity categorie);
    @Mapping(source="articles", target = "articles", ignore = true)
    CategorieEntity mapToEntity(Categorie categorie);
  @Mapping(source="articles", target = "articles", ignore = true)
    List<CategorieEntity> mapToEntities(List<Categorie> categories);

    @Mapping(source="articles", target = "articles", ignore = true)
    List<Categorie> mapToModels(List<CategorieEntity> categories);
}


