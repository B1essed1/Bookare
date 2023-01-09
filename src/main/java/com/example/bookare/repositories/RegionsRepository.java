package com.example.bookare.repositories;

import com.example.bookare.entities.Regions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionsRepository extends JpaRepository<Regions, Long> {
}
