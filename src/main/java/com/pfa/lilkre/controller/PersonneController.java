package com.pfa.lilkre.controller;

import com.pfa.lilkre.model.Image;
import com.pfa.lilkre.model.Personne;
import com.pfa.lilkre.repository.*;
import com.pfa.lilkre.services.intf.IPersonneService;
import com.pfa.lilkre.services.intf.IimagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/personne")
public class PersonneController {
    @Autowired
    IPersonneService personneService;
    @Autowired
    PersonneRepository personneRepository;
    @Autowired
    PanierRepository panierRepository;

    @Autowired
    IimagesService iimagesService;

    @GetMapping("/search")
    @Operation(summary = "Récupérer la liste des personnes ")
    public ResponseEntity<Page<Personne>> listBysearch(
            @ParameterObject Pageable pageable,
            @Parameter(description = "le critère de recherche") @RequestParam String search) {
        Page<Personne> personnePage = personneService.getSearch(pageable, search);
        if (personnePage.hasContent()) {
            return ResponseEntity.ok(personnePage);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
	/*@GetMapping("/searchCommandes")
	@Operation(summary = "Récupérer la liste des personnes qui ont du commandes ")
	public ResponseEntity<Page<Personne>> listCommandes(
			@ParameterObject Pageable pageable ){

		Page<Personne> personnePage = personneService.Searchcmmandes(pageable);
		if (personnePage.hasContent()){
			return ResponseEntity.ok(personnePage);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}*/

    @GetMapping
    public ResponseEntity<Page<Personne>> list(@ParameterObject Pageable pageable) {
        Page<Personne> personnePage = personneService.getAll(pageable);
        if (personnePage.hasContent()) {
            return ResponseEntity.ok(personnePage);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{id}")
    public Optional<Personne> getPersonne(@PathVariable Long id) {
        return personneService.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public Personne save(@RequestPart("personne") Personne personne,
                         @RequestPart("files") MultipartFile[] files) throws IOException {
        Set<Image> imgs = iimagesService.uploadImage(files);
        personne.setImages(new ArrayList<>(imgs));
        return personneService.save(personne);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public Personne update(@RequestPart("personne") Personne personne,
                           @RequestPart("files") MultipartFile[] files) throws IOException {

        Set<Image> imgs = iimagesService.uploadImage(files);
        personne.setImages(new ArrayList<>(imgs));


        return personneService.update(personne);

    }
	/*@PutMapping
	public Personne update(@RequestBody Personne personne) {
		return personneService.update(personne);
	}*/

}
