package com.pfa.lilkre.controller;
import com.pfa.lilkre.entities.GouvernoratEntity;
import com.pfa.lilkre.services.intf.IGouvernoratService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Gouvernorat")
public class GouvernoratController {
    @Autowired
    IGouvernoratService iGouvernoratService;

    @GetMapping
    public ResponseEntity<Page<GouvernoratEntity>> list(@ParameterObject Pageable pageable) {
        Page<GouvernoratEntity> GouvernoratEntityPage = iGouvernoratService.getAll(pageable);
        if (GouvernoratEntityPage.hasContent()) {
            return ResponseEntity.ok(GouvernoratEntityPage);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


    @GetMapping("/{id}")
    public Optional<GouvernoratEntity> getGouvernoratEntity(@PathVariable Long id) {
        return iGouvernoratService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        iGouvernoratService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody GouvernoratEntity gouvernoratEntity) {
        iGouvernoratService.save(gouvernoratEntity);
    }
}
