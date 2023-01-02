package com.example.bookare.services;

import com.example.bookare.entities.Users;
import com.example.bookare.entities.UsersReserve;
import com.example.bookare.models.LoginDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersService {
    public Users castToUsers(UsersReserve reserve);
    public void save(Users users);
    Optional<Users> getUsersByEmail(String email);
    Optional<Users> findUserByEmailAndPassword(LoginDto loginDto);
}
