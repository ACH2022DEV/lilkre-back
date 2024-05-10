package com.pfa.lilkre.mappers;

import com.pfa.lilkre.entities.PersonneEntity;
import com.pfa.lilkre.model.Personne;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {PanierMapper.class, AvisMapper.class, ImagesMapper.class, CommandeMapper.class})
public interface PersonneMapper {

    PersonneMapper INSTANCE = Mappers.getMapper(PersonneMapper.class);

    Personne mapToModel(PersonneEntity personne);


    PersonneEntity mapToEntity(Personne personne);


    List<PersonneEntity> mapToEntities(List<Personne> personnes);


    List<Personne> mapToModels(List<PersonneEntity> personnes);
    // nouveau ligne
   /* Page<Personne> mapToModels(Page<PersonneEntity> personneEntityPage);
    Page<PersonneEntity> mapToEntities(Page<Personne> personneEntityPage);*/


}
