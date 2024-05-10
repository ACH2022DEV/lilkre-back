package com.pfa.lilkre.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "avis")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AvisEntity implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Integer etoile;

    @ManyToOne
    @JoinColumn(name = "Avis_PersonneId")
    @JsonBackReference
    private PersonneEntity personne;
    @ManyToOne
    @JoinColumn(name = "Avis_ArticleId")
    @JsonBackReference
    private ArticleEntity article;
    private LocalDateTime dateAvis;


}
