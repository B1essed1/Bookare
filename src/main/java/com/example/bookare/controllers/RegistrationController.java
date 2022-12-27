package com.example.bookare.controllers;

import com.example.bookare.entities.Users;
import com.example.bookare.entities.UsersReserve;
import com.example.bookare.models.ConfirmRegDto;
import com.example.bookare.models.RegUserDto;
import com.example.bookare.models.ResponseDto;
import com.example.bookare.repositories.UsersReserveRepository;
import com.example.bookare.security.JwtTokenCreator;
import com.example.bookare.services.ReserveUsersService;
import com.example.bookare.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/reg/")
public class RegistrationController {

    private final ReserveUsersService reserveUsersService;
    private final UsersReserveRepository reserveRepository;
    private final UsersService usersService;
    private final JavaMailSender javaMailSender;

    private final JwtTokenCreator jwtTokenCreator;

    /**
     TODO
     Messages should be externalized and translated in two languages ENG/RU
     */


     /***
     *  TODO
     * In order to start, used only required fields to implement registration
     * Should update when changes will be done!
     * */


    @PostMapping("registration")
    @Transactional
    public ResponseEntity<?> registration(@RequestBody RegUserDto dto){
        ResponseDto<UsersReserve> response= reserveUsersService.castToUsers(dto);
        if (response.getIsError()){
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(response.getMessage());
        }

        UsersReserve reserve = response.getData();
        reserveRepository.save(reserve);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Tasdiqlash kodi");
        mailMessage.setText(reserve.getOtp().toString());
        mailMessage.setTo(reserve.getEmail());
        javaMailSender.send(mailMessage);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response.getData().getEmail());
    }


    @PostMapping("confirm")
    public ResponseEntity<?> confirmation(@RequestBody ConfirmRegDto dto){
        Optional<UsersReserve> reserve = reserveRepository.findUsersReserveByEmail(dto.getEmail());
        if (reserve.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("bunday emaildagi foydalanuvchi mavjud emas");

        if (Objects.equals(reserve.get().getOtp(), dto.getOtp())){
            Long diff =  new Date().getTime() - reserve.get().getCreatedDate().getTime();
            if (diff<=120000){

                Users users = usersService.castToUsers(reserve.get());
                usersService.save(users);
                return ResponseEntity.status(HttpStatus.CREATED).body(JwtTokenCreator.createJwtToken(users));
            }else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("otp time out error");
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("otp xato kiritilgan!");
        }
    }

    @GetMapping("refresh/token")
    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResponseDto result = jwtTokenCreator.refreshTokens(request);

        if (!result.getIsError()){
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), result.getData());
        } else {
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), result.getMessage());
        }
    }

}
