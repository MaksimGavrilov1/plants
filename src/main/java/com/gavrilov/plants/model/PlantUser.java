package com.gavrilov.plants.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "plant_user")
@Getter
@Setter
public class PlantUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy="user")
    @JsonManagedReference
    private Set<Container> containers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<Role>(Collections.singleton(role));
    }

    @Override
    public @NonNull String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
