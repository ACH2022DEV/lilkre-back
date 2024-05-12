package com.pfa.lilkre.services.impl;

import com.pfa.lilkre.entities.CommuneEntity;
import com.pfa.lilkre.entities.GouvernoratEntity;
import com.pfa.lilkre.repository.CommuneRepository;
import com.pfa.lilkre.repository.GouvernoratRepository;
import com.pfa.lilkre.services.intf.IGouvernoratService;
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
public class GouvernoratServiceImpl implements IGouvernoratService {
    @Autowired
    GouvernoratRepository repository;

    @Override
    public Page<GouvernoratEntity> getAll(Pageable pageable) {

        Page<GouvernoratEntity> GouvernoratEntityPage = repository.findAll(pageable);
        List<GouvernoratEntity> GouvernoratEntityList = GouvernoratEntityPage.stream().toList();
        List<GouvernoratEntity> gouvernoratList =GouvernoratEntityList.stream().toList();


        return new PageImpl<>(gouvernoratList, pageable, GouvernoratEntityPage.getTotalElements());
    }

    public Optional<GouvernoratEntity> findById(Long id) {
        return repository.findById(id);
    }


    public GouvernoratEntity save(GouvernoratEntity gouvernoratEntity) {
        return repository.save(gouvernoratEntity);
    }

    public GouvernoratEntity update(GouvernoratEntity gouvernoratEntity) {
        return repository.save(gouvernoratEntity);
    }


    public boolean delete(Long id) {
        Optional<GouvernoratEntity> ar = repository.findById(id);
        if (ar.isPresent()) {
            repository.deleteById(id);
        }
        return true;
    }
}
