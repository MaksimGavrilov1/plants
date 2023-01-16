package com.gavrilov.plants.service;

import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.repository.PlantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PlantUserRepository plantUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PlantUser user = plantUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found");
        }
        return user;
    }
}
