package com.pfa.lilkre.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Panier")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "personne")
public class PanierEntity implements Serializable {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "personneId")
    @JsonBackReference
    private PersonneEntity personne;

    @ManyToOne
    @JoinColumn(name = "articleId")
    @JsonManagedReference
    private ArticleEntity article;


    private int quantity;


    private LocalDateTime date;


}
