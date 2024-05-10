package com.pfa.lilkre.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Envies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "personne")
public class EnviesListEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "personne_id")
    @JsonBackReference
    private PersonneEntity personne;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

}
