package com.example.bookare.services;

import com.example.bookare.entities.UsersReserve;
import com.example.bookare.models.RegUserDto;
import com.example.bookare.models.ResponseDto;

public interface ReserveUsersService{

    public ResponseDto<UsersReserve> castToUsers(RegUserDto dto);
}
