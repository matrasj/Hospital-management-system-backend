package com.example.hospitalmanagementsystembackend.model.payload.drug;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugPayload {
    private Long id;
    private String name;
    private String companyBrand;
}
