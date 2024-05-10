package com.pfa.lilkre.repository;

import com.pfa.lilkre.entities.ConfirmationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCodeEntity, Long>, JpaSpecificationExecutor<ConfirmationCodeEntity> {

    List<ConfirmationCodeEntity> findByEmailOrderByCreatedAtDesc(String email);
}
