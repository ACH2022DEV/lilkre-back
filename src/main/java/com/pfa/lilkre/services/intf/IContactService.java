package com.pfa.lilkre.services.intf;

import com.pfa.lilkre.entities.dto.CreateContactDto;
import com.pfa.lilkre.model.Contact;

import java.util.List;
import java.util.Optional;

public interface IContactService {
    public List<Contact> getAll();

    public Optional<Contact> findById(Long id);

    public void save(CreateContactDto createContactDto);

    public boolean delete(Long id);


}
