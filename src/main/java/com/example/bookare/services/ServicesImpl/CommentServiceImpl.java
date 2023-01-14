package com.example.bookare.services.ServicesImpl;

import com.example.bookare.entities.Comments;
import com.example.bookare.entities.Ratings;
import com.example.bookare.entities.Users;
import com.example.bookare.exceptions.ResourceNotFoundException;
import com.example.bookare.models.ApiResponse;
import com.example.bookare.models.CommentDto;
import com.example.bookare.repositories.CommentsRepository;
import com.example.bookare.repositories.UsersRepository;
import com.example.bookare.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UsersRepository userRepository;
    private final CommentsRepository commentRepository;

    @Override
    public ApiResponse<?> saveComment(CommentDto commentDto) {

        Comments comment = new Comments();
        Long commenter_id = commentDto.getCommenter_id();
        Long user_id = commentDto.getUser_id();

        Users commenter = userRepository
                .findById(commenter_id)
                .orElseThrow(() -> new ResourceNotFoundException("commenter", "id", commenter_id));

        Users user = userRepository
                .findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", user_id));

        comment.setCommenter(commenter);
        comment.setUser(user);
        comment.setText(commentDto.getComment());
        comment.setDate(new Date());
        Comments saved = commentRepository.save(comment);

        return ApiResponse.builder()
                .data(saved)
                .success(true)
                .message("Comment Saved! ")
                .build();
    }
}
