package com.example.hospitalmanagementsystembackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctor_profession")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorProfession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "doctorProfession")
    private Set<Doctor> doctors = new HashSet<>();
}