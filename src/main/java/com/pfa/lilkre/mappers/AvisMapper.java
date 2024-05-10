package com.pfa.lilkre.mappers;

import com.pfa.lilkre.entities.AvisEntity;
import com.pfa.lilkre.model.Avis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ArticleMapper.class, PersonneMapper.class})

public interface AvisMapper {
    AvisMapper INSTANCE = Mappers.getMapper(AvisMapper.class);

    @Mapping(source = "article", target = "article", ignore = true)//par moi
    @Mapping(source = "personne.cammandes", target = "personne.cammandes", ignore = true)//par moi

    @Mapping(source = "personne.paniers", target = "personne.paniers", ignore = true)//par moi
    @Mapping(source = "personne.role", target = "personne.role", ignore = true)//par moi
    @Mapping(source = "personne.avis", target = "personne.avis", ignore = true)
//par moi

    Avis mapToModel(AvisEntity avis);

    @Mapping(source = "personne.cammandes", target = "personne.cammandes", ignore = true)//par moi

    @Mapping(source = "personne.paniers", target = "personne.paniers", ignore = true)//par moi
    @Mapping(source = "article", target = "article", ignore = true)//par moi
    @Mapping(source = "personne.role", target = "personne.role", ignore = true)//par moi
    @Mapping(source = "personne.avis", target = "personne.avis", ignore = true)
//par moi


    AvisEntity mapToEntity(Avis avis);

    @Mapping(source = "personne.cammandes", target = "personne.cammandes", ignore = true)//par moi

    @Mapping(source = "personne.paniers", target = "personne.paniers", ignore = true)//par moi
    @Mapping(source = "article", target = "article", ignore = true)//par moi
    @Mapping(source = "personne.role", target = "personne.role", ignore = true)//par moi
    @Mapping(source = "personne.avis", target = "personne.avis", ignore = true)
//par moi


    List<AvisEntity> mapToEntities(List<Avis> avisList);

    @Mapping(source = "personne.cammandes", target = "personne.cammandes", ignore = true)//par moi

    @Mapping(source = "personne.paniers", target = "personne.paniers", ignore = true)//par moi
    @Mapping(source = "article", target = "article", ignore = true)//par moi
    @Mapping(source = "personne.role", target = "personne.role", ignore = true)//par moi

    @Mapping(source = "personne.avis", target = "personne.avis", ignore = true)
//par moi


    List<Avis> mapToModels(List<AvisEntity> avisList);
}

