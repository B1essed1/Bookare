package com.example.bookare.services;

import com.example.bookare.models.ApiResponse;
import com.example.bookare.models.RatingDto;

public interface RatingService {
    ApiResponse<?> saveRating(RatingDto rating);

    ApiResponse<?> getOneUserRating(Long user_id);
}
