package com.pfa.lilkre.services.impl;

import com.pfa.lilkre.entities.PersonneEntity;
import com.pfa.lilkre.mappers.PersonneMapper;
import com.pfa.lilkre.model.Personne;
import com.pfa.lilkre.repository.PersonneRepository;
import com.pfa.lilkre.services.intf.IPersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonneServiceImpl implements IPersonneService {

    @Autowired
    private PersonneRepository personneRepository;


    public Personne save(Personne personne) {
        return PersonneMapper.INSTANCE.mapToModel(personneRepository.save(PersonneMapper.INSTANCE.mapToEntity(personne)));
    }

    //ajouter pour le moment save Personne Entity
    public PersonneEntity savePersonneEntity(PersonneEntity personne) {
        return personneRepository.save(personne);
    }

    @Override
    public Page<Personne> getSearch(Pageable pageable, String keyword) {

        Page<PersonneEntity> personneEntityPage = personneRepository.findBySearch(pageable, keyword);
        List<PersonneEntity> personneEntityList = personneEntityPage.stream().toList();
        List<Personne> personneListList =
                PersonneMapper.INSTANCE.mapToModels(personneEntityList.stream().toList());


        return new PageImpl<>(personneListList, pageable, personneEntityPage.getTotalElements());
    }
 /*   @Override
    public Page<Personne> Searchcmmandes(Pageable pageable) {

        Page<PersonneEntity> personneEntityPage = personneRepository.SearchCommandes(pageable);
        List<PersonneEntity> personneEntityList = personneEntityPage.stream().toList();
        List<Personne> personneListList =
                PersonneMapper.INSTANCE.mapToModels(personneEntityList.stream().toList());


        return new PageImpl<>(personneListList, pageable, personneEntityPage.getTotalElements());
    }*/


    @Override
    public Page<Personne> getAll(Pageable pageable) {

        Page<PersonneEntity> personneEntityPage = personneRepository.findAll(pageable);
        List<PersonneEntity> personneEntityList = personneEntityPage.stream().toList();
        List<Personne> personneListList =
                PersonneMapper.INSTANCE.mapToModels(personneEntityList.stream().toList());


        return new PageImpl<>(personneListList, pageable, personneEntityPage.getTotalElements());
    }

    @Override
    public Optional<Personne> findById(Long id) {
        return personneRepository.findById(id).map(PersonneMapper.INSTANCE::mapToModel);
    }

    public Personne update(Personne personne) {
        // ajouter un test pour vérifier si la personne existe en base
        return PersonneMapper.INSTANCE.mapToModel(personneRepository.save(PersonneMapper.INSTANCE.mapToEntity(personne)));
    }


    public boolean delete(Long id) {
        Optional<PersonneEntity> a = personneRepository.findById(id);
        if (a.isPresent()) {
            personneRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean ifEmailExist(String mail) {
        return personneRepository.existsByEmail(mail);
    }

    public boolean ifUsernameExist(String username) {
        return personneRepository.existsByUsername(username);
    }

    public boolean ifTelephoneExist(String tel) {
        return personneRepository.existsByTel(tel);
    }

    public PersonneEntity getUserByMail(String mail) {
        return personneRepository.findByEmail(mail);
    }

    public PersonneEntity getUserByTel(String tel) {
        return personneRepository.findByTel(tel);
    }

    public Optional<PersonneEntity> getUserByUsername(String username) {
        return personneRepository.findByUsername(username);
    }

}
