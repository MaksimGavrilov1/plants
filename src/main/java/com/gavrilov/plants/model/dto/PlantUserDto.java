package com.gavrilov.plants.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantUserDto {

    private String firstName;
    private String lastName;
    private String middleName;
    private String username;
    private String password;
    private String confirmPassword;

}
