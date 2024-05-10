package com.pfa.lilkre.controller;

import com.pfa.lilkre.entities.EnviesListEntity;
import com.pfa.lilkre.entities.dto.CreatedEnviesList;
import com.pfa.lilkre.services.intf.IEnviesService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Envies")
public class EnviesController {
    @Autowired
    IEnviesService iEnviesService;

    @GetMapping
    public ResponseEntity<Page<EnviesListEntity>> list(@ParameterObject Pageable pageable) {
        Page<EnviesListEntity> enviesPage = iEnviesService.getAll(pageable);
        if (enviesPage.hasContent()) {
            return ResponseEntity.ok(enviesPage);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{id}")
    public Optional<EnviesListEntity> getEnviesList(@PathVariable Long id) {
        return iEnviesService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody CreatedEnviesList enviesList) {

        iEnviesService.save(enviesList);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        iEnviesService.delete(id);
    }

}
