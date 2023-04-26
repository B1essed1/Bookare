package com.example.bookare.services;

import com.example.bookare.models.ApiResponse;
import com.example.bookare.models.RatingDto;
import com.example.bookare.models.ResponseDto;

public interface RatingService {
    ResponseDto<?> saveRating(RatingDto rating);

    ResponseDto<?> getAllRatings();

    ResponseDto<?> getOneUserRating(Long user_id);

    ResponseDto<?> deleteRating(Long rating_id);

    ResponseDto<?> updateRating(Long rating_id, RatingDto ratingDto);
}
