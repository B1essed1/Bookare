package com.example.bookare.services;

import com.example.bookare.models.ApiResponse;
import com.example.bookare.models.RatingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * project: my_part
 * author: Sardor Urokov
 * created at 5:30 PM on 21/Dec/2022
 **/
public interface RatingService {
    ApiResponse<?> saveRating(RatingDto rating);

    ApiResponse<?> getOneUserRating(Long user_id);
}
