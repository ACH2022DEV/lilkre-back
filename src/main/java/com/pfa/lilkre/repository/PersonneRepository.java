package com.pfa.lilkre.repository;

import com.pfa.lilkre.entities.PersonneEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PersonneRepository extends JpaRepository<PersonneEntity, Long>, JpaSpecificationExecutor<PersonneEntity> {

    Optional<PersonneEntity> findByUsername(String username);

    public PersonneEntity findByEmail(String email);

    public PersonneEntity findByTel(String tel);

    Boolean existsByUsername(String username);

    Boolean existsByTel(String tel);

    Boolean existsByEmail(String email);

    //Page<PersonneEntity> findByNom(Pageable pageable);
    @Query("select per from PersonneEntity per where " + "CONCAT(per.id,per.nom,per.prenom,per.adress,per.tel) " + "LIKE CONCAT( '%',:keyword,'%')")
    public Page<PersonneEntity> findBySearch(Pageable pageable, String keyword);
    /*@Query("select per from PersonneEntity per where per.commandes  in (select com from CommandeEntity com where com.lenght= null)")
    public Page<PersonneEntity> SearchCommandes(Pageable pageable);
*/
}
