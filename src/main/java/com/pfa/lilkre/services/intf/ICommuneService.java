package com.pfa.lilkre.services.intf;


import com.pfa.lilkre.entities.CommuneEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICommuneService {
    public Page<CommuneEntity> getAll(Pageable pageable);

    public Optional<CommuneEntity> findById(Long id);

    public CommuneEntity save(CommuneEntity communeEntity);

    public CommuneEntity update(CommuneEntity communeEntity);

    public boolean delete(Long id);
}
