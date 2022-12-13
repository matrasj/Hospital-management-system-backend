package com.example.hospitalmanagementsystembackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;

@Entity
@Table(name = "doctor")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @ManyToOne(cascade = {
            DETACH, MERGE
    })
    @JoinColumn(name = "doctor_profession_id", referencedColumnName = "id")
    private DoctorProfession doctorProfession;
}