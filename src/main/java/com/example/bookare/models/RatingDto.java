package com.example.bookare.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private Integer rating;
    private String comment;
    private Long rater_id;
    private Long user_id;
}
