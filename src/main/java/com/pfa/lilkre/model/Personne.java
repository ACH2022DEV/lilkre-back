package com.pfa.lilkre.model;

import com.pfa.lilkre.entities.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Personne {

    @Schema(name = "id", description = "l'identifiant technique de l'objet personne ")
    private Long id;
    @Schema(name = "nom", description = "le nom de  personne ")
    @NotBlank
    @Size(min = 0, max = 15)
    private String nom;
    @Schema(name = "prenom", description = "le prenom de  personne ")
    @NotBlank
    @Size(min = 0, max = 10)
    private String prenom;
    @Schema(name = "adress", description = "l'adress de  personne ")
    @NotBlank
    @Size(min = 0, max = 25)
    private String adress;
    @Schema(name = "tel", description = "le Numero telephone de  personne ")
    @NotBlank
    @Size(min = 0, max = 8)
    private String tel;
    @Schema(name = "username", description = "le nom de personne en tant que utilisateur ")
    @NotBlank
    @Size(min = 0, max = 10)
    private String username;
    @Schema(name = "email", description = "l'email de  personne ")
    @NotBlank
    @Size(min = 0, max = 35)
    private String email;
    @Schema(name = "password", description = "le password de  personne ")
    @NotBlank
    @Size(min = 0, max = 15)
    private String password;
    @Schema(name = "role", description = "le role de  personne ")
    @NotBlank
    @Size(min = 0, max = 15)
    private RoleEntity role;
    @Schema(name = "paniers", description = "la liste des paniers de  personne ")
    private List<Panier> paniers;
    @Schema(name = "images", description = "la liste des images de  personne ")
    private List<Image> images;
    @Schema(name = "Avis", description = "la liste des Avis de  personne ")
    private List<Avis> avis;
    private List<Commande> cammandes;
}
