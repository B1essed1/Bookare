package com.example.bookare.repositories;

import com.example.bookare.entities.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {

    @Query(value = "SELECT AVG(ratings.rate) FROM ratings where ratings.user_id = :user_id",
            nativeQuery = true)
        // ma'lum bir id lik userning rating ini topish uchun
    Optional<Object> getUserRating(@Param("user_id") Long user_id);

    @Query(value = "select r from ratings r where r.rater_id = :rater_id and r.user_id = :user_id",
            nativeQuery = true)
    // ma'lum bir rater avval ham shu id lik userga rate berganmi yoki yo'qligini tekshirish uchun
    boolean findRatingsByRaterIdAndUserId(@Param("rater_id") Long raterId, @Param("user_id") Long userId);

    List<Ratings> findAllByUser_id(Long user_id);
}
