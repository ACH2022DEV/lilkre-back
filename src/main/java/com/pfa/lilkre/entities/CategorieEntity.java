package com.pfa.lilkre.entities;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categorie")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategorieEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
    private List<ArticleEntity> articles;
}
