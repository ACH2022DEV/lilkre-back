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
public class CreateContactDto {
    private Long idPersonne;
    private List<ContactezDto> contact;
}
