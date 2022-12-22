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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingsRepository ratingsRepository;
    private final UsersRepository usersRepository;
    private final CommentServiceImpl commentService;

    @Override
    public ApiResponse<?> saveRating(RatingDto ratingDto) {
        Ratings rating = new Ratings();

        Integer maxRating = 5; //rating maximum 5ga teng bo'lishi kerak
        Long user_id = ratingDto.getUser_id();
        Long rater_id = ratingDto.getRater_id();

        Users rater = usersRepository
                .findById(rater_id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", rater_id));

        Users user = usersRepository
                .findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", user_id));

        boolean isRated = ratingsRepository
                .findRatingsByRaterIdAndUserId(rater_id, user_id); //userga aynan shu rater tomonidan avval rate berilganmi yoki yo'qligini bazadan qidiradi

        if (!isRated && ratingDto.getRating() <= maxRating) { //agar ushbu id(user_id) lik userga shu id(rater_id) lik rater tomonidan avval rating berilmagan bo'lsa va rating 5 dan kichik bo'lsa save qiladi
            rating.setRater(rater);
            rating.setUser(user);
            rating.setRate(ratingDto.getRating());
            if (rating.getComment() != null) {
                rating.setComment(ratingDto.getComment());

                CommentDto commentDto = new CommentDto();
                commentDto.setComment(ratingDto.getComment());
                commentDto.setUser_id(user_id);
                commentDto.setCommenter_id(rater_id);
                commentService.saveComment(commentDto);
            }
            Ratings save = ratingsRepository.save(rating);

            return ApiResponse.builder()
                    .data(save)
                    .success(true)
                    .message("Comment Saved!")
                    .build();
        } else { //aks holda message jo'natiladi

            String warningMessage = "The rater with this id: " + rater_id
                    + " is already have rated this user: %s !" + user_id;

            return ApiResponse.builder()
                    .success(false)
                    .message(warningMessage)
                    .build();
        }
    }

    @Override
    public ApiResponse<?> getOneUserRating(Long user_id) {
        float user_rating = (float) ratingsRepository
                .getUserRating(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", user_id));

        return ApiResponse.builder()
                .data(user_rating)
                .success(true)
                .message("OneUserRating")
                .build();
    }
}
