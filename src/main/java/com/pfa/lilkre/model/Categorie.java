package com.pfa.lilkre.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Categorie {
    private Long id;
    private String nom;
    private List<Article> articles;
}
