package com.pfa.lilkre.repository;


import com.pfa.lilkre.entities.CategorieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategorieRepository extends JpaRepository<CategorieEntity, Long>, JpaSpecificationExecutor<CategorieEntity> {
}
