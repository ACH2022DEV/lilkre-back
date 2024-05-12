package com.pfa.lilkre.services.impl;
import com.pfa.lilkre.entities.CommuneEntity;
import com.pfa.lilkre.repository.CommuneRepository;
import com.pfa.lilkre.services.intf.ICommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class CommuneServiceImpl  implements ICommuneService {
    @Autowired
    CommuneRepository repository;

    @Override
    public Page<CommuneEntity> getAll(Pageable pageable) {

        Page<CommuneEntity> CommuneEntityPage = repository.findAll(pageable);
        List<CommuneEntity> communeEntityList = CommuneEntityPage.stream().toList();
        List<CommuneEntity> CategorieListList =communeEntityList.stream().toList();


        return new PageImpl<>(CategorieListList, pageable, CommuneEntityPage.getTotalElements());
    }

    public Optional<CommuneEntity> findById(Long id) {
        return repository.findById(id);
    }


    public CommuneEntity save(CommuneEntity communeEntity) {
        return repository.save(communeEntity);
    }

    public CommuneEntity update(CommuneEntity communeEntity) {
        return repository.save(communeEntity);
    }


    public boolean delete(Long id) {
        Optional<CommuneEntity> ar = repository.findById(id);
        if (ar.isPresent()) {
            repository.deleteById(id);
        }
        return true;
    }
}
