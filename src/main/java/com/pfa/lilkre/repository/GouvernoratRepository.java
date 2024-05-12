package com.pfa.lilkre.repository;

import com.pfa.lilkre.entities.GouvernoratEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GouvernoratRepository extends JpaRepository<GouvernoratEntity, Long>, JpaSpecificationExecutor<GouvernoratEntity> {
}
