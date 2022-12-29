package com.example.bookare.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  ResponseDto <T> {

    private T data;

    private String message;

    private Boolean isError;


}
