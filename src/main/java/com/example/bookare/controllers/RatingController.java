package com.example.bookare.controllers;

import com.example.bookare.models.RatingDto;
import com.example.bookare.models.ResponseDto;
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
    public ResponseEntity<?> getOneUserRating(@PathVariable Long id) {
        ResponseDto<?> userRating = ratingService.getOneUserRating(id);
        return new ResponseEntity<>(userRating.getData(), HttpStatus.OK);
    }

    @PostMapping("saveRating")
    public ResponseEntity<?> saveUserRating(@RequestBody RatingDto ratingDto) {

        boolean isRated = ratingsRepository.findRatingsByRaterIdAndUserId(ratingDto.getRater_id(), ratingDto.getUser_id());

        if (!isRated ) {
            ResponseDto<?> apiResponse = ratingService.saveRating(ratingDto);
            return ResponseEntity
                    .status(!apiResponse.getIsError() ? 201 : 409)
                    .body(apiResponse);
        } else {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("You already has rated this User!");
        }
    }
}
