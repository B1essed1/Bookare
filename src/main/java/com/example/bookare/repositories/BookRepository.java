package com.example.bookare.repositories;

import com.example.bookare.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByActiveTrue();

    Optional<Book> findByIdAndActiveTrue(Long along);
}
