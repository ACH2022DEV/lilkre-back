package com.pfa.lilkre.controller;

import com.pfa.lilkre.entities.CommuneEntity;
import com.pfa.lilkre.entities.GouvernoratEntity;
import com.pfa.lilkre.entities.dto.CommuneWithGouvernoratDTO;
import com.pfa.lilkre.services.intf.ICommuneService;
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
@RequestMapping("/Commune")
public class CommuneController {
    @Autowired
    ICommuneService iCommuneService;

    @GetMapping
    public ResponseEntity<Page<CommuneEntity>> list(@ParameterObject Pageable pageable) {
        Page<CommuneEntity> CommuneEntityPage = iCommuneService.getAll(pageable);
        if (CommuneEntityPage.hasContent()) {
            return ResponseEntity.ok(CommuneEntityPage);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<CommuneWithGouvernoratDTO> getCommuneEntity(@PathVariable Long id) {
        Optional<CommuneEntity> communeOptional = iCommuneService.findById(id);
        if (communeOptional.isPresent()) {
            CommuneEntity commune = communeOptional.get();
            GouvernoratEntity gouvernorat = commune.getGouvernorat();
            CommuneWithGouvernoratDTO communeDTO = new CommuneWithGouvernoratDTO();
            communeDTO.setCommuneId(commune.getId());
            communeDTO.setCommuneNom(commune.getNom());
            if(gouvernorat!=null){
                communeDTO.setGouvernoratId(gouvernorat.getId());
                communeDTO.setGouvernoratNom(gouvernorat.getNom());
            }
            return ResponseEntity.ok(communeDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        iCommuneService.delete(id);
    }

    @PostMapping
    public void save(@RequestBody CommuneEntity communeEntity) {
        iCommuneService.save(communeEntity);
    }
    @PutMapping
    public void update(@RequestBody CommuneEntity communeEntity) {
        iCommuneService.update(communeEntity);
    }
}
