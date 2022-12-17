package com.example.bookare.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmRegDto {
    private String email;
    private Integer otp;
}
