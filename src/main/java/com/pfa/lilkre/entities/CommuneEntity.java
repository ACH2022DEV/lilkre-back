package com.pfa.lilkre.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "Commune")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommuneEntity  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    // Autres attributs de la commune...

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gouvernorat_id")
    private GouvernoratEntity gouvernorat;

}
