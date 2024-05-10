package com.pfa.lilkre.controller;

import com.pfa.lilkre.entities.dto.CreateContactDto;
import com.pfa.lilkre.model.Contact;
import com.pfa.lilkre.repository.ContactRepository;
import com.pfa.lilkre.repository.PersonneRepository;
import com.pfa.lilkre.services.intf.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    IContactService iContactService;

    @GetMapping
    public List<Contact> list() {
        return iContactService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Contact> getContact(@PathVariable Long id) {
        return iContactService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        iContactService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody CreateContactDto createContactDto) {
        iContactService.save(createContactDto);
    }


}
