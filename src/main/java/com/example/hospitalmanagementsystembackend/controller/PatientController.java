package com.example.hospitalmanagementsystembackend.controller;

import com.example.hospitalmanagementsystembackend.model.entity.Patient;
import com.example.hospitalmanagementsystembackend.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PatientController {
    private final PatientService patientService;
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.status(OK)
                .body(patientService.findAllPatients());
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Patient>> getPatientsPage(@RequestParam int pageSize,
                                                         @RequestParam int pageNumber) {
        return ResponseEntity.status(OK)
                .body(patientService.findPatientsPage(pageNumber, pageSize));
    }

    @GetMapping("/pagination/findByLastNameKeywordContaining")
    public ResponseEntity<Page<Patient>> getPatientsPageFilteredByLastNameKeywordContaining(@RequestParam int pageSize,
                                                                                            @RequestParam int pageNumber,
                                                                                            @RequestParam String lastNameKeyword) {
        return ResponseEntity.status(OK)
                .body(patientService.findPatientsPageFilteredByLastNameKeyword(pageNumber, pageSize, lastNameKeyword));
    }
}
