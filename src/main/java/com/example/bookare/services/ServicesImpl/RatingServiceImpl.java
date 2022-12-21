package com.example.bookare.services.ServicesImpl;

import com.example.bookare.entities.Ratings;
import com.example.bookare.entities.Users;
import com.example.bookare.exceptions.ResourceNotFoundException;
import com.example.bookare.models.ApiResponse;
import com.example.bookare.models.CommentDto;
import com.example.bookare.models.RatingDto;
import com.example.bookare.repositories.RatingsRepository;
import com.example.bookare.repositories.UsersRepository;
import com.example.bookare.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingsRepository ratingsRepository;
    private final UsersRepository usersRepository;
    private final CommentServiceImpl commentService;

    @Override
    public ApiResponse<?> saveRating(RatingDto ratingDto) {
        Ratings rating = new Ratings();

        Users rater = usersRepository
                .findById(ratingDto.getRater_id())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", ratingDto.getRater_id()));

        Users user = usersRepository
                .findById(ratingDto.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", ratingDto.getUser_id()));

            rating.setRater(rater);
            rating.setUser(user);
            rating.setRate(ratingDto.getRating());
            if (rating.getComment() != null){
                CommentDto commentDto = new CommentDto();
                commentDto.setComment(ratingDto.getComment());
                commentDto.setUser_id(ratingDto.getUser_id());
                commentDto.setCommenter_id(ratingDto.getRater_id());
                commentService.saveComment(commentDto);
            }
            rating.setComment(ratingDto.getComment());
            Ratings save = ratingsRepository.save(rating);

       return ApiResponse.builder()
                .data(save)
                .success(true)
                .message("Comment Saved!")
                .build();
    }

    @Override
    public ApiResponse<?> getOneUserRating(Long user_id) {
        Long user_rating = (Long) ratingsRepository
                .getUserRating(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", user_id));

        return ApiResponse.builder()
                .data(user_rating)
                .success(true)
                .message("GetOne")
                .build();
    }
}
