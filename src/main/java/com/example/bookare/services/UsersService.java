package com.example.bookare.services;

import com.example.bookare.entities.Users;
import com.example.bookare.entities.UsersReserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersService {
    public Users castToUsers(UsersReserve reserve);
    public void save(Users users);
}
