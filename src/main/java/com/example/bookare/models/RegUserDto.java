package com.example.bookare.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegUserDto {
    private String name;
    private String surname;
    private String password;
    private String email;
    private String role = "USER"; //set USER role by default 2-variant
//    private String role;


}
