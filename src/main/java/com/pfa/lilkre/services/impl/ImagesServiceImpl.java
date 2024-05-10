package com.pfa.lilkre.services.impl;

import com.pfa.lilkre.entities.ImagesEntity;
import com.pfa.lilkre.mappers.ImagesMapper;
import com.pfa.lilkre.model.Image;
import com.pfa.lilkre.repository.ImagesRepository;
import com.pfa.lilkre.services.intf.IimagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class ImagesServiceImpl implements IimagesService {
    @Autowired
    ImagesRepository imagesRepository;

    public Set<Image> uploadImage(MultipartFile[] file) throws IOException {
        Set<Image> images = uplod(file);
        return  images;
    }


    public Set<Image> uplod(MultipartFile[] multipartFiles) throws IOException {

        Set<Image> images = new HashSet<>();
        for (MultipartFile file : multipartFiles) {
            Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
            images.add(image);
        }
        return images;

    }

    public Optional<Image> getImage(long id) {
        Optional<ImagesEntity> images = imagesRepository.findById(id);
        return imagesRepository.findById(id).map(ImagesMapper.INSTANCE::mapToModel);
    }

    public List<Image> getALLimages() {
        return ImagesMapper.INSTANCE.mapToModels(imagesRepository.findAll());
    }

    public boolean delete(Long id) {
        Optional<ImagesEntity> ar = imagesRepository.findById(id);
        if (ar.isPresent()) {
            imagesRepository.deleteById(id);
        }
        return true;
    }


}
