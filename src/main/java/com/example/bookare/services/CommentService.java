package com.example.bookare.services;

import com.example.bookare.models.ApiResponse;
import com.example.bookare.models.CommentDto;

public interface CommentService {
    ApiResponse<?> saveComment(CommentDto commentDto);
}
