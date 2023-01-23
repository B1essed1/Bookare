package com.example.bookare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Getter
@Setter
public class RegUserDto {
    private MultipartFile photo;

    private String name;
    private String surname;
    private String password;
    private String email;
    private String role = "USER"; //set U
    //private Datas data;


    @Column(nullable = false)
    private String districtName;
    @Column(nullable = false)
    private String quartersName;
    @Column(nullable = false)
    private String regionsName;
}
