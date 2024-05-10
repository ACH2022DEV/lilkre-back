package com.pfa.lilkre.services.intf;

import com.pfa.lilkre.entities.dto.CreateAvisDto;
import com.pfa.lilkre.model.Avis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAvisService {
    public void save(CreateAvisDto avis);

    public boolean delete(Long id);

    public Optional<Avis> findById(Long id);

    public List<Avis> getAll();

    public Page<Avis> getSearch(Pageable pageable, Integer NbEtoile);
}
