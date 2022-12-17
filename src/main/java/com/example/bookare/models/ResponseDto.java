package com.example.bookare.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  ResponseDto <T> {

    private T data;

    private String message;

    private Boolean isError;


}
