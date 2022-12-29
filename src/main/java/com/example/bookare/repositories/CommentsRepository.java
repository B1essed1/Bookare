package com.example.bookare.repositories;

import com.example.bookare.entities.Comments;
import com.example.bookare.entities.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentsRepository extends JpaRepository<Comments, Long> {

}
