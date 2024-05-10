package com.pfa.lilkre.mappers;

import com.pfa.lilkre.entities.ImagesEntity;
import com.pfa.lilkre.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ImagesMapper {
    ImagesMapper INSTANCE = Mappers.getMapper(ImagesMapper.class);

    Image mapToModel(ImagesEntity images);

    @Mapping(target = "id", source = "id", ignore = true)
    ImagesEntity mapToEntity(Image images);

    List<ImagesEntity> mapToEntities(List<Image> listimages);

    List<Image> mapToModels(List<ImagesEntity> listimages);
}
