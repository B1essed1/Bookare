package com.example.bookare.services.ServicesImpl;

import com.example.bookare.entities.Photo;
import com.example.bookare.entities.UsersReserve;
import com.example.bookare.models.ConfirmRegDto;
import com.example.bookare.models.RegUserDto;
import com.example.bookare.models.ResponseDto;
import com.example.bookare.repositories.PhotoRepository;
import com.example.bookare.repositories.UsersReserveRepository;
import com.example.bookare.services.BookService;
import com.example.bookare.services.ReserveUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReserveUsersServiceImpl implements ReserveUsersService {

    private  final UsersReserveRepository usersReserveRepository;
    private final BookService bookService;
    private final PhotoRepository photoRepository;
    @Override
    @Transactional
    public ResponseDto<UsersReserve> castToUsers(RegUserDto dto) throws Exception {
      //  Datas dto = data.getData();

       Optional<UsersReserve> check =  usersReserveRepository.findUsersReserveByEmail(dto.getEmail());
       ResponseDto response = new ResponseDto();
       if (check.isPresent()){
           response.setMessage("Foydalanuvchi bu elektron pochta bilan avval ro'yxatdan o'tgan!");
           response.setIsError(true);
       } else {
           try {
               Random random = new Random();

               UsersReserve users = new UsersReserve();
               users.setEmail(dto.getEmail());
               if (dto.getRole() == null)
                   // users.setRole("USER"); //set USER role by default 1-variant
                   users.setRole(dto.getRole());
               users.setCreatedDate(new Date());
               users.setName(dto.getName());
               users.setSurname(dto.getSurname());
               users.setPassword(dto.getPassword());
               users.setOtp(random.nextInt(89999) + 10000);
               users.setDistrictName(dto.getDistrictName());
               users.setQuartersName(dto.getQuartersName());
               users.setRegionsName(dto.getQuartersName());


               //adding profile photo to users profile;
               String[] split = Objects.requireNonNull(dto.getPhoto().getOriginalFilename()).split("\\.");
               String extension = split[split.length - 1];
               String fileName = UUID.randomUUID() + "." + extension;

               Photo savedPhoto = Photo.builder()
                       .url(bookService.upload(dto.getPhoto(), fileName))
                       .name(fileName)
                       .build();
               photoRepository.save(savedPhoto);
               users.setPhoto(savedPhoto);

               response.setData(users);
               response.setIsError(false);
           } catch (Exception e){
               throw new Exception(e);
           }
       }

       return response;
    }

    @Override
    public Optional<UsersReserve>   save(UsersReserve reserve) {
        return Optional.of(usersReserveRepository.save(reserve));
    }

    @Transactional
    public ResponseDto<?> resendOtp( ConfirmRegDto confirmRegDto){
        ResponseDto apiResponse = new ResponseDto();
        try {
            ConfirmRegDto confirmationDto = new ConfirmRegDto();
            UsersReserve usersReserve = usersReserveRepository.findUsersReserveByEmail(confirmRegDto.getEmail()).get();
            Random random = new Random();
            Integer otp = random.nextInt(8999) + 1000;
            usersReserve.setOtp(otp);
            usersReserveRepository.save(usersReserve);
            confirmationDto.setEmail(confirmRegDto.getEmail());
            confirmationDto.setOtp(otp);

            apiResponse.setData(confirmationDto);
            apiResponse.setIsError(false);
        } catch (Exception e){
            apiResponse.setIsError(true);
            apiResponse.setMessage(e.getMessage());
        }
        return apiResponse;
    }

    @Override
    public Optional<UsersReserve> findUsersReserveByEmail(String email) {
        return usersReserveRepository.findUsersReserveByEmail(email);
    }
}
