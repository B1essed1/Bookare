package com.example.bookare.repositories;

import com.example.bookare.entities.Quarters;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuartersRepository extends JpaRepository<Quarters, Long> {
    List<Quarters> findAllByDistrictsId(Long districts_id);
}
