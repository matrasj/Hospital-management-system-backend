package com.example.hospitalmanagementsystembackend.controller;

import com.example.hospitalmanagementsystembackend.model.payload.drug.CompanyWithDrugsQuantityPayload;
import com.example.hospitalmanagementsystembackend.model.payload.drug.DrugPayload;
import com.example.hospitalmanagementsystembackend.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/drugs")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DrugController {

    private final DrugService drugService;
    @GetMapping("/companies-quantity")
    public ResponseEntity<List<CompanyWithDrugsQuantityPayload>> getMostPopularDrugBrands(@RequestParam Long limit) {
        return ResponseEntity.status(OK)
                .body(drugService.findMostPopularBrandsWithDrugsQuantity(limit));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<DrugPayload>> getDrugsPage(@RequestParam int pageSize,
                                                          @RequestParam int pageNumber) {
        return ResponseEntity.status(OK)
                .body(drugService.findDrugsPage(pageNumber, pageSize));
    }

    @GetMapping("/pagination/findByKeywordContaining")
    public ResponseEntity<Page<DrugPayload>> getDrugsPageByKeywordContaining(@RequestParam int pageNumber,
                                                                             @RequestParam int pageSize,
                                                                             @RequestParam String keyword) {
        return ResponseEntity.status(OK)
                .body(drugService.findDrugsPageByKeywordContaining(pageNumber, pageSize, keyword));
    }
 }
