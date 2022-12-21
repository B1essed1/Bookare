package com.example.bookare.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "ratings")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rate;
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    private Users rater;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
/*
@Entity(
    name = "ratings"
)
public class Ratings {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;
    private Integer rating;
    private String comment;
    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = {CascadeType.REFRESH}
    )
    private Users user;
    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = {CascadeType.REFRESH}
    )
    private Users rater;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
 */