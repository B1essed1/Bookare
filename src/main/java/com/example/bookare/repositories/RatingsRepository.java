package com.example.bookare.repositories;

import com.example.bookare.entities.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {

    @Query( value = "SELECT AVG(ratings.rate) FROM ratings where ratings.user_id=:user_id",
            nativeQuery = true)
    Optional<Object> getUserRating(@Param("user_id") Long user_id);

    Optional<Ratings> findByUser_id(Long user_id);
}
