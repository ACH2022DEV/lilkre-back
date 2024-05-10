package com.pfa.lilkre.services.intf;

import com.pfa.lilkre.entities.EnviesListEntity;
import com.pfa.lilkre.entities.dto.CreatedEnviesList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IEnviesService {
    public Page<EnviesListEntity> getAll(Pageable pageable);

    public Optional<EnviesListEntity> findById(Long id);

    public void save(CreatedEnviesList envies);

    public boolean delete(Long id);
}
