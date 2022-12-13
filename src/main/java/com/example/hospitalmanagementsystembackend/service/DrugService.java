package com.example.hospitalmanagementsystembackend.service;

import com.example.hospitalmanagementsystembackend.mapper.DrugPayloadMapper;
import com.example.hospitalmanagementsystembackend.model.entity.Drug;
import com.example.hospitalmanagementsystembackend.model.payload.drug.CompanyWithDrugsQuantityPayload;
import com.example.hospitalmanagementsystembackend.model.payload.drug.DrugPayload;
import com.example.hospitalmanagementsystembackend.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugService {
    private final DrugRepository drugRepository;

    public List<CompanyWithDrugsQuantityPayload> findMostPopularBrandsWithDrugsQuantity(Long limit) {
        List<Object[]> brandsWithDrugsQuantity = drugRepository.findBrandsWithDrugsQuantity(limit);

        return brandsWithDrugsQuantity.stream()
                .map((objects -> new CompanyWithDrugsQuantityPayload(
                        (String) (objects[0]),
                        (BigInteger) (objects[1])
                )))
                .toList();
    }

    public Page<DrugPayload> findDrugsPage(int pageNumber, int pageSize) {
        List<DrugPayload> collect = drugRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(DrugPayloadMapper::mapToDrugPayload)
                .toList();

        return new PageImpl<>(collect,
                PageRequest.of(pageNumber, pageSize),
                drugRepository.findAll().size());
    }

    public Page<DrugPayload> findDrugsPageByKeywordContaining(@RequestParam int pageNumber,
                                                              @RequestParam int pageSize,
                                                              @RequestParam String keyword) {
        Page<Drug> byNameContaining = drugRepository.findByNameContaining(keyword, PageRequest.of(pageNumber, pageSize));

        List<DrugPayload> collect = byNameContaining.stream()
                .map(DrugPayloadMapper::mapToDrugPayload)
                .toList();

        return new PageImpl<>(
                collect,
                PageRequest.of(pageNumber, pageSize),
                byNameContaining.getTotalElements()
        );
    }
}
