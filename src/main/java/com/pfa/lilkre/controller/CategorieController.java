package com.pfa.lilkre.controller;
import com.pfa.lilkre.entities.CategorieEntity;
import com.pfa.lilkre.model.Categorie;
import com.pfa.lilkre.services.intf.ICategorieService;
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
@RequestMapping("/Categorie")
public class CategorieController {
    @Autowired
    ICategorieService iCategorieService;

    @GetMapping
    public ResponseEntity<Page<CategorieEntity>> list(@ParameterObject Pageable pageable) {
        Page<CategorieEntity> categoriePage = iCategorieService.getAll(pageable);
        if (categoriePage.hasContent()) {
            return ResponseEntity.ok(categoriePage);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


    @GetMapping("/{id}")
    public Optional<CategorieEntity> getCategorie(@PathVariable Long id) {
        return iCategorieService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        iCategorieService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody CategorieEntity categorie) {
        iCategorieService.save(categorie);
    }
}
