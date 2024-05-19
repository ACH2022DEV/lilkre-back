package com.pfa.lilkre.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString()
public class Categorie {
    private Long id;
    private String nom;
    private List<Article> articles;
}
