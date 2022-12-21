package com.example.bookare.services;

import com.example.bookare.models.ApiResponse;
import com.example.bookare.models.CommentDto;

/**
 * project: my_part
 * author: Sardor Urokov
 * created at 5:27 PM on 21/Dec/2022
 **/
public interface CommentService {
    ApiResponse<?> saveComment(CommentDto commentDto);
}
