package com.example.hospitalmanagementsystembackend.service;

import com.example.hospitalmanagementsystembackend.model.entity.Patient;
import com.example.hospitalmanagementsystembackend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    public List<Patient> findAllPatients() {
        return patientRepository.findAllPatientsWithMaleOrFemaleGender();
    }

    public Page<Patient> findPatientsPage(int pageNumber, int pageSize) {
        return patientRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
