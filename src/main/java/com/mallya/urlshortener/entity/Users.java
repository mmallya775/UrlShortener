package com.mallya.urlshortener.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class Users {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    @ElementCollection
    @CollectionTable(
            name = "authorities",
            joinColumns = @JoinColumn(name = "username")
    )
    @Column(name = "authority")
    private Set<String> authorities = new HashSet<>();
}
