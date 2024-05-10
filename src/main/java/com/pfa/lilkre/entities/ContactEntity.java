package com.pfa.lilkre.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Contact")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactEntity implements Serializable {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private String destinateur;
    @ManyToOne
    @JoinColumn(name = "Contact_PersonneId")
    private PersonneEntity personne;
    @Column(nullable = false)
    private String sujet;
    private LocalDateTime dateContact;
}
