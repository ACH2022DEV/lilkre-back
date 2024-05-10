package com.pfa.lilkre.services.intf;

import com.pfa.lilkre.entities.PersonneEntity;
import com.pfa.lilkre.model.Personne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPersonneService {

    /**
     * Cette méthode permet de créer une personne
     *
     * @param personne
     * @return
     */
    public Personne save(Personne personne);

    public PersonneEntity savePersonneEntity(PersonneEntity personne);

    /**
     * Cette fonction peremt de retourner la liste des personnes
     *
     * @return
     */
    public Page<Personne> getAll(Pageable pageable);

    public Optional<Personne> findById(Long id);


    public Personne update(Personne personne);


    public boolean delete(Long id);

    public Page<Personne> getSearch(Pageable pageable, String keyword);

    //public Page<Personne> Searchcmmandes(Pageable pageable);
    public boolean ifEmailExist(String mail);

    public boolean ifUsernameExist(String username);

    public PersonneEntity getUserByMail(String mail);

    public boolean ifTelephoneExist(String tel);

    public PersonneEntity getUserByTel(String tel);

    public Optional<PersonneEntity> getUserByUsername(String username);
}
