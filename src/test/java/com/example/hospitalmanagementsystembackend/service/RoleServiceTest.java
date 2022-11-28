package com.example.hospitalmanagementsystembackend.service;

import com.example.hospitalmanagementsystembackend.model.entity.Authority;
import com.example.hospitalmanagementsystembackend.model.entity.Role;
import com.example.hospitalmanagementsystembackend.model.entity.User;
import com.example.hospitalmanagementsystembackend.repository.UserRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;

@AutoConfigureTestDatabase

class RoleServiceTest {
    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleService roleService;

    void findRolesAssignedToAccountTest() {
        User user = buildUser();
        Set<Role> testRoles = getTestRole();
        Set<Authority> testAuthorities = getTestAuthorities();
        testRoles
                .forEach((role -> {
                    role.setAuthorities(testAuthorities);
                }));

        testAuthorities
                        .forEach(authority -> {
                            authority.setRoles(testRoles);
                        });

        user.setRoles(testRoles);

        given(userRepository.findByUsername("pudzian"))
                .willReturn(Optional.of(user));


    }

    private User buildUser() {
        return User.builder()
                .firstName("Mariusz")
                .lastName("Pudzianowski")
                .username("pudzian")
                .password("pudzian")
                .email("mariusz.pudzian@gmail.com")
                .build();
    }

    private Set<Authority> getTestAuthorities() {
        return Stream.of(
                Authority.builder()
                        .id(1L)
                        .permission("READ")
                        .build(),
                Authority.builder()
                        .id(2L)
                        .permission("DELETE")
                        .build()
        ).collect(Collectors.toCollection(HashSet::new));
    }

    private Set<Role> getTestRole() {
        return Collections.singleton(Role.builder()
                .name("ADMIN")
                .build());
    }



}