package com.example.hospitalmanagementsystembackend.service;


import com.example.hospitalmanagementsystembackend.model.payload.drug.CompanyWithDrugsQuantityPayload;
import com.example.hospitalmanagementsystembackend.repository.DrugRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class DrugServiceTest {
    @Mock
    DrugRepository drugRepository;

    @InjectMocks
    DrugService drugService;


    @Test
    void findMostPopularBrandsWithDrugsQuantity() {
        List<Object[]> brandWithBiggestAmountOfDrugs = List.of(
                new Object[]{"Physicians Total Care, Inc.", BigInteger.valueOf(28L)},
                new Object[]{"Nelco Laboratories, Inc.", BigInteger.valueOf(25L)},
                new Object[]{"REMEDYREPACK INC.", BigInteger.valueOf(22L)},
                new Object[]{"Cardinal Health", BigInteger.valueOf(19L)},
                new Object[]{"ALK-Abello, Inc.", BigInteger.valueOf(12L)}
        );

        given(drugRepository.findBrandsWithDrugsQuantity(anyLong()))
                .willReturn(brandWithBiggestAmountOfDrugs);

        List<CompanyWithDrugsQuantityPayload> mostPopularBrandsWithDrugsQuantity = drugService.findMostPopularBrandsWithDrugsQuantity(5L);
        assertAll("List test",
            () -> assertEquals(brandWithBiggestAmountOfDrugs.get(0)[0], mostPopularBrandsWithDrugsQuantity.get(0).getCompanyBrand()),
            () -> assertEquals(brandWithBiggestAmountOfDrugs.get(0)[1], mostPopularBrandsWithDrugsQuantity.get(0).getDrugsQuantity()),
            () -> assertEquals(brandWithBiggestAmountOfDrugs.get(4)[0], mostPopularBrandsWithDrugsQuantity.get(4).getCompanyBrand()),
            () -> assertEquals(brandWithBiggestAmountOfDrugs.get(4)[1], mostPopularBrandsWithDrugsQuantity.get(4).getDrugsQuantity())

        );
