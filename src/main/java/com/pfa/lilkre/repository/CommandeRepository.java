package com.pfa.lilkre.repository;

import com.pfa.lilkre.entities.CommandeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommandeRepository extends JpaRepository<CommandeEntity, Long>, JpaSpecificationExecutor<CommandeEntity> {
}
