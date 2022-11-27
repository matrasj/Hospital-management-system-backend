package com.example.hospitalmanagementsystembackend.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(cascade = {
            DETACH, MERGE
    }, fetch = LAZY)
    @JoinTable(name = "user_role",
    joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")
    })
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = ALL)
    private Set<ConfirmationToken> tokens = new HashSet<>();

    public Set<Authority> getAuthorities() {
        return roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }


}
