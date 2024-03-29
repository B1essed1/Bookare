package com.example.bookare.controllers;

import com.azure.core.annotation.Put;
import com.example.bookare.models.RatingDto;
import com.example.bookare.models.ResponseDto;
import com.example.bookare.services.ServicesImpl.RatingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ratings/")
public class RatingController {

    private final RatingServiceImpl ratingService;

    @GetMapping("{id}")
    public ResponseEntity<?> getOneUserRating(@PathVariable Long id) {
        ResponseDto<?> userRating = ratingService.getOneUserRating(id);
        return new ResponseEntity<>(userRating, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> allRatings() {
        ResponseDto<?> allRatings = ratingService.getAllRatings();
        return ResponseEntity.ok(allRatings);
    }

    @PostMapping("saveRating")
    public ResponseEntity<?> saveUserRating(@RequestBody RatingDto ratingDto) {

        ResponseDto<?> apiResponse = ratingService.saveRating(ratingDto);
        return ResponseEntity
                .status(apiResponse.getIsError() ? 409 : 201)
                .body(apiResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRating(@PathVariable Long id, @RequestBody RatingDto ratingDto){
        ResponseDto<?> response = ratingService.updateRating(id, ratingDto);
        return ResponseEntity.status(response.getIsError() ? 200 : 404).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRating(@PathVariable Long id) {

        ResponseDto<?> apiResponse = ratingService.deleteRating(id);
        return ResponseEntity
                .status(apiResponse.getIsError() ? 409 : 200)
                .body(apiResponse);
    }
}