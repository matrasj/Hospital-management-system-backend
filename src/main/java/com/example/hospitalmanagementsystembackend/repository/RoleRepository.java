package com.example.hospitalmanagementsystembackend.repository;

import com.example.hospitalmanagementsystembackend.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
