package com.example.bookare.repositories;

import com.example.bookare.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByBookIdAndActiveTrue(Long bookId);

    Optional<Photo> findByIdAndActiveTrue(Long id);
}
