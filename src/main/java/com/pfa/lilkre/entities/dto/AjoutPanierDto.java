package com.pfa.lilkre.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AjoutPanierDto {
    private Long idPersonne;
    private PanierDto paniers;

}
