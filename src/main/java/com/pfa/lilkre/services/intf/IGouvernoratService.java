package com.pfa.lilkre.services.intf;

import com.pfa.lilkre.entities.GouvernoratEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGouvernoratService {
    public Page<GouvernoratEntity> getAll(Pageable pageable);

    public Optional<GouvernoratEntity> findById(Long id);

    public GouvernoratEntity save(GouvernoratEntity gouvernoratEntity);

    public GouvernoratEntity update(GouvernoratEntity gouvernoratEntity);

    public boolean delete(Long id);
}
