package com.example.bookare.controllers;

import com.example.bookare.models.ApiResponse;
import com.example.bookare.models.RatingDto;
import com.example.bookare.repositories.RatingsRepository;
import com.example.bookare.services.ServicesImpl.RatingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ratings/")
public class RatingController {

    private final RatingsRepository ratingsRepository;
    private final RatingServiceImpl ratingService;

    @GetMapping("{id}")
    public ResponseEntity<?> getOneUserRating(@PathVariable Long id){
        ApiResponse<?> userRating = ratingService.getOneUserRating(id);
        return new ResponseEntity<>(userRating, HttpStatus.OK);
    }

    @PostMapping("saveRating")
    public ResponseEntity<?> saveUserRating (@RequestBody RatingDto ratingDto) {
        ApiResponse<?> apiResponse = ratingService.saveRating(ratingDto);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

}
