package com.pfa.lilkre.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "Gouvernorat")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GouvernoratEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;
    @OneToMany(mappedBy = "gouvernorat", cascade = CascadeType.ALL)
    private List<CommuneEntity> communes;
}
