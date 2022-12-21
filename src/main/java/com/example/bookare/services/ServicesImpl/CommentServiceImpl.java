package com.example.bookare.services.ServicesImpl;

import com.example.bookare.models.ApiResponse;
import com.example.bookare.models.CommentDto;
import com.example.bookare.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Override
    public ApiResponse<?> saveComment(CommentDto commentDto) {
        return null;
    }
}
