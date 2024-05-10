package com.pfa.lilkre.mappers;

import com.pfa.lilkre.entities.PanierEntity;
import com.pfa.lilkre.model.Panier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(uses = {PersonneMapper.class, ArticleMapper.class})
public interface PanierMapper {
    PanierMapper INSTANCE = Mappers.getMapper(PanierMapper.class);

    @Mapping(source = "personne", target = "personne", ignore = true)//par moi
    @Mapping(source = "article.avis", target = "article.avis", ignore = true)
//par moi

    Panier mapToModel(PanierEntity panier);

    @Mapping(source = "personne", target = "personne", ignore = true)//par moi
    @Mapping(source = "article.avis", target = "article.avis", ignore = true)
//par moi

    PanierEntity mapToEntity(Panier panier);

    List<PanierEntity> mapToEntities(List<Panier> listpanier);

    @Mapping(source = "personne", target = "personne", ignore = true)//par moi
    @Mapping(source = "article.avis", target = "article.avis", ignore = true)
//par moi

    List<Panier> mapToModels(List<PanierEntity> listpanier);
}
