package com.pfa.lilkre.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAvisDto {
    private Long idPersonne;
    private List<AvisDto> avis;
}
