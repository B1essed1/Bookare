package com.example.bookare.services;

import com.example.bookare.entities.UsersReserve;
import com.example.bookare.models.ConfirmRegDto;
import com.example.bookare.models.RegUserDto;
import com.example.bookare.models.ResponseDto;

import java.io.IOException;
import java.util.Optional;

public interface ReserveUsersService{

    public ResponseDto<UsersReserve> castToUsers(RegUserDto dto) throws Exception;

    Optional<UsersReserve> save(UsersReserve reserve);

    public ResponseDto<?> resendOtp(ConfirmRegDto confirmRegDto);

    Optional<UsersReserve> findUsersReserveByEmail(String email);

}
