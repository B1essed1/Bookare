package com.example.bookare.repositories;

import com.example.bookare.entities.Districts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictsRepository extends JpaRepository<Districts, Long> {
    List<Districts> findAllByRegionsId(Long regions_id);
}
