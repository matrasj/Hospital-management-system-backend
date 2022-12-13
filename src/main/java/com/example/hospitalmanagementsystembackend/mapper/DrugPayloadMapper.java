package com.example.hospitalmanagementsystembackend.mapper;

import com.example.hospitalmanagementsystembackend.model.entity.Drug;
import com.example.hospitalmanagementsystembackend.model.payload.drug.DrugPayload;

public class DrugPayloadMapper {
    public static DrugPayload mapToDrugPayload(Drug drug) {
        return DrugPayload.builder()
                .id(drug.getId())
                .name(drug.getName())
                .companyBrand(drug.getCompanyBrand())
                .build();

    }
}
