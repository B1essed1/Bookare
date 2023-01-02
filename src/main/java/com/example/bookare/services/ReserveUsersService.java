package com.example.bookare.services;

import com.example.bookare.entities.UsersReserve;
import com.example.bookare.models.ApiResponse;
import com.example.bookare.models.ConfirmRegDto;
import com.example.bookare.models.RegUserDto;
import com.example.bookare.models.ResponseDto;

import java.util.Optional;

public interface ReserveUsersService{

    public ResponseDto<UsersReserve> castToUsers(RegUserDto dto);

    Optional<UsersReserve> save(UsersReserve reserve);

    public ApiResponse<?> resendOtp(ConfirmRegDto confirmRegDto);


}
