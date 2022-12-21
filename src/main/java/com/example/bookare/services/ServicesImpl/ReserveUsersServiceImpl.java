package com.example.bookare.services.ServicesImpl;

import com.example.bookare.entities.Users;
import com.example.bookare.entities.UsersReserve;
import com.example.bookare.models.RegUserDto;
import com.example.bookare.models.ResponseDto;
import com.example.bookare.repositories.UsersReserveRepository;
import com.example.bookare.services.ReserveUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ReserveUsersServiceImpl implements ReserveUsersService {

    private  final UsersReserveRepository usersReserveRepository;
    @Override
    @Transactional
    public ResponseDto<UsersReserve> castToUsers(RegUserDto dto) {
       Optional<UsersReserve> check =  usersReserveRepository.findUsersReserveByEmail(dto.getEmail());
       ResponseDto response = new ResponseDto();
       if (check.isPresent()){
           response.setMessage("Foydalanuvchi bu elektron pochta bilan avval ro'yxatdan o'tgan!");
           response.setIsError(true);
       } else {
           Random random = new Random();

           UsersReserve users = new UsersReserve();
           users.setEmail(dto.getEmail());
           if (dto.getRole() == null)
//               users.setRole("USER"); //set USER role by default 1-variant
           users.setRole(dto.getRole());
           users.setCreatedDate(new Date());
           users.setName(dto.getName());
           users.setSurname(dto.getSurname());
           users.setPassword(dto.getPassword());
           users.setOtp(random.nextInt(89999)+10000);

           response.setData(users);
           response.setIsError(false);
       }

       return response;
    }
}
