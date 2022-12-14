package com.example.hospitalmanagementsystembackend.service;

import com.example.hospitalmanagementsystembackend.exception.UserNotFoundException;
import com.example.hospitalmanagementsystembackend.model.entity.Patient;
import com.example.hospitalmanagementsystembackend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PatientService {
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user with id %d";
    private final PatientRepository patientRepository;
    public List<Patient> findAllPatients() {
        return patientRepository.findAllPatientsWithMaleOrFemaleGender();
    }

    public Page<Patient> findPatientsPage(int pageNumber, int pageSize) {
        return patientRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public Page<Patient> findPatientsPageFilteredByLastNameKeyword(int pageNumber, int pageSize, String lastNameKeyword) {
        return patientRepository.findByLastNameContaining(lastNameKeyword, PageRequest.of(pageNumber, pageSize));
    }

    public Patient updatePatientField(Long patientId, Map<String, Object> fieldsMap) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, patientId)));

        fieldsMap.forEach((key, value) -> {
            try {
                Field field = ReflectionUtils.findField(Patient.class, key);
                Objects.requireNonNull(field).setAccessible(true);
                ReflectionUtils.setField(field, patient, value);
            } catch (Exception exception) {
                throw new RuntimeException("Field " + key + " does not exist");
            }

        });

        patientRepository.save(patient);

        return patient;
    }
}
