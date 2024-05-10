package com.pfa.lilkre.mappers;

import com.pfa.lilkre.entities.ContactEntity;
import com.pfa.lilkre.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = PersonneMapper.class)
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    Contact mapToModel(ContactEntity contact);

    ContactEntity mapToEntity(Contact contact);

    List<ContactEntity> mapToEntities(List<Contact> contactList);

    List<Contact> mapToModels(List<ContactEntity> contactList);


}
