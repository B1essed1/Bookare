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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
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
                .orElseThrow(() -> new ResourceNotFoundException("rater", "id", rater_id));

        Users user = usersRepository
                .findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", user_id));

        Float isRated = ratingsRepository
                .findRateByRaterIdAndUserId(ratingDto.getRater_id(), ratingDto.getUser_id());


        if (ratingDto.getRating() <= maxRating && isRated == null) {
            rating.setRater(rater);
            rating.setUser(user);
            rating.setDate(new Date());
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
                    .message(myMessage)
                    .isError(false)
                    .data(saved)
                    .build();

        } else if (isRated != null) {
            myMessage = "The Rater is already had rated this User!";

            return ResponseDto.builder()
                    .isError(true)
                    .message(myMessage)
                    .build();
        } else {
            myMessage = "Rating value must be 5 maximum!";
            return ResponseDto.builder()
                    .isError(true)
                    .message(myMessage)
                    .build();
        }
    }

    @Override
    public ResponseDto<?> getAllRatings() {
        List<Ratings> allRatings = ratingsRepository.findAllRatingsOrderByRate();
        return ResponseDto.builder()
                .data(allRatings)
                .isError(false)
                .message("All Ratings")
                .build();
    }

    @Override
    public ResponseDto<?> getOneUserRating(Long user_id) {
        float user_rating = ratingsRepository
                .getUserRating(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("rating", "user_id", user_id));

        return ResponseDto.builder()
                .data(user_rating)
                .isError(false)
                .message("Rating of User with " + user_id + " id")
                .build();
    }

    @Override
    public ResponseDto<?> deleteRating(Long rating_id) {

        boolean isDeleted = ratingsRepository.existsById(rating_id);

        if (!isDeleted)
            return ResponseDto.builder()
                    .message("Rating not found!")
                    .isError(true)
                    .build();
        else
            ratingsRepository.deleteById(rating_id);

        return ResponseDto.builder()
                .message("Deleted!")
                .isError(false)
                .build();
    }

    @Override
    public ResponseDto<?> updateRating(Long rating_id, RatingDto ratingDto) {

        Optional<Ratings> byId = ratingsRepository.findById(rating_id);
        Ratings rating = byId.get();

        Long user_id = ratingDto.getUser_id();
        Long rater_id = ratingDto.getRater_id();

        Users rater = usersRepository
                .findById(rater_id)
                .orElseThrow(() -> new ResourceNotFoundException("rater", "id", rater_id));

        Users user = usersRepository
                .findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", user_id));

        rating.setRater(rater);
        rating.setUser(user);
        rating.setDate(new Date());
        rating.setRate(rating.getRate());
        Ratings saved = ratingsRepository.save(rating);

        return ResponseDto.builder()
                .message("Rating Updated!")
                .isError(false)
                .data(saved)
                .build();
    }
}