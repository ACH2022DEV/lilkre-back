package com.pfa.lilkre.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommuneWithGouvernoratDTO {
    private Long communeId;
    private String communeNom;
    private Long gouvernoratId;
    private String gouvernoratNom;
}
