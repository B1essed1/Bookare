package com.example.bookare.services;

import com.example.bookare.models.CommentDto;
import com.example.bookare.models.ResponseDto;

public interface CommentService {
    ResponseDto<?> saveComment(CommentDto commentDto);
}
