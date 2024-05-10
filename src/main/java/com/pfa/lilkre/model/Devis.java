package com.pfa.lilkre.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Devis {
    @Schema(name = "codedevis", description = "l'identifiant technique de l'objet devis ")

    private Long codedevis;
    @Schema(name = "personne", description = "l'objet personne de cette devis  ")

    private Personne personne;
    @Schema(name = "articles", description = "la liste des articles dans un devis  ")

    private List<DevisArticle> articles;

}
