package com.example.hospitalmanagementsystembackend.repository;

import com.example.hospitalmanagementsystembackend.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "SELECT * FROM patient WHERE patient.gender='Male' OR patient.gender='Female'", nativeQuery = true)
    List<Patient> findAllPatientsWithMaleOrFemaleGender();
}
