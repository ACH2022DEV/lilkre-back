package com.pfa.lilkre.repository;


import com.pfa.lilkre.entities.CommuneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommuneRepository extends JpaRepository<CommuneEntity, Long>, JpaSpecificationExecutor<CommuneEntity> {
}
