package com.example.bookare.services.ServicesImpl;

import com.example.bookare.entities.Ratings;
import com.example.bookare.entities.Users;
import com.example.bookare.exceptions.ResourceNotFoundException;
import com.example.bookare.models.CommentDto;
import com.example.bookare.models.RatingDto;
import com.example.bookare.models.ResponseDto;
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
    public ResponseDto<?> saveRating(RatingDto ratingDto) {
        Ratings rating = new Ratings();

        Integer maxRating = 5; //rating maximum 5ga teng bo'lishi kerak
        String myMessage = "Rating Saved!";
        Long user_id = ratingDto.getUser_id();
        Long rater_id = ratingDto.getRater_id();

        Users rater = usersRepository
                .findById(rater_id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", rater_id));

        Users user = usersRepository
                .findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", user_id));

        if (ratingDto.getRating() <= maxRating) {
            rating.setRater(rater);
            rating.setUser(user);
            rating.setRate(ratingDto.getRating());
            if (ratingDto.getComment() != null) {

                CommentDto commentDto = new CommentDto();
                commentDto.setComment(ratingDto.getComment());
                commentDto.setUser_id(user_id);
                commentDto.setCommenter_id(rater_id);
                commentService.saveComment(commentDto);

                myMessage = "Rating and Comment saved!";
            }
            Ratings saved = ratingsRepository.save(rating);

            return ResponseDto.builder()
                    .data(saved)
                    .isError(true)
                    .message(myMessage)
                    .build();
        } else {
            myMessage = "Rating value must be 5 maximum!";
            return ResponseDto.builder()
                    .isError(false)
                    .message(myMessage)
                    .build();
        }
    }

    @Override
    public ResponseDto<?> getOneUserRating(Long user_id) {
        float user_rating = (float) ratingsRepository
                .getUserRating(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", user_id));

        return ResponseDto.builder()
                .data(user_rating)
                .isError(true)
                .message("OneUserRating ")
                .build();
    }
}