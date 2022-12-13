package com.example.hospitalmanagementsystembackend.model.payload.drug;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyWithDrugsQuantityPayload {
   private String companyBrand;
   private BigInteger drugsQuantity;
}
