package com.example.bookare.repositories;

import com.example.bookare.entities.UsersReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UsersReserveRepository extends JpaRepository<UsersReserve, Long> {
    Optional<UsersReserve> findUsersReserveByEmail(String email);

    @Override
    @Transactional
    <S extends UsersReserve> S save(S entity);
}
