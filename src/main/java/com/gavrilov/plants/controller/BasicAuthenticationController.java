package com.gavrilov.plants.controller;

import com.gavrilov.plants.model.PlantUser;
import com.gavrilov.plants.model.enums.Role;
import com.gavrilov.plants.model.Site;
import com.gavrilov.plants.model.dto.PlantUserDto;
import com.gavrilov.plants.repository.PlantUserRepository;
import com.gavrilov.plants.repository.SiteRepository;
import com.gavrilov.plants.utils.AuthenticationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins={ "http://localhost:3000", "http://localhost:4200" })
@RestController
public class BasicAuthenticationController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PlantUserRepository userRepository;
    @Autowired
    private SiteRepository siteRepository;

    @GetMapping(path = "/basicauth")
    public AuthenticationBean authenticate() {
        return new AuthenticationBean("You are authenticated");
    }

    @GetMapping("/auth/username/exists")
    public ResponseEntity<String> isUsernameExists(@RequestBody String username) {
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.status(418).body("User already exists");
        } else {
            return ResponseEntity.ok("");
        }
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody PlantUserDto user) {
//        if (userRepository)
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("user with this username already exists");
        }
        Site siteFromDB = siteRepository.save(new Site());
        PlantUser newUser = new PlantUser();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setMiddleName(user.getMiddleName());
        newUser.setRole(Role.ROLE_ADMIN);
        newUser.setSite(siteFromDB);
        if (user.getUsername() == null) {
            return ResponseEntity.badRequest().body("USERNAME NULL");
        }
        userRepository.save(newUser);

        return ResponseEntity.ok("User created");
    }
}
