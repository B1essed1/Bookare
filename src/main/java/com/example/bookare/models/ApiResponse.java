package com.example.bookare.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * project: my_part
 * author: Sardor Urokov
 * created at 5:28 PM on 21/Dec/2022
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{
    private String message;
    private T data;
    private boolean success;

    public ApiResponse(T data, boolean success) {
        this.data = data;
        this.success = success;
    }
}
