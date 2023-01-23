package com.example.bookare.services;

import com.example.bookare.models.RatingDto;
import com.example.bookare.models.ResponseDto;
import io.swagger.models.Response;

public interface RatingService {
    ResponseDto<?> saveRating(RatingDto rating);

    ResponseDto<?> getOneUserRating(Long user_id);
}
