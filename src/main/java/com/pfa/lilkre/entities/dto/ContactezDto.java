package com.pfa.lilkre.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactezDto {
    private String message;
    private String sujet;
    private String destinateur;
}
