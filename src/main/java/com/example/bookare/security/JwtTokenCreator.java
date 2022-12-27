package com.example.bookare.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bookare.entities.Roles;
import com.example.bookare.entities.Users;
import com.example.bookare.models.ResponseDto;
import com.example.bookare.services.UsersService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.bookare.utils.Constants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class JwtTokenCreator {
    @Autowired
    private UsersService usersService;


    public  static Map<String,String> createJwtToken(Users users){
        Map<String , String> tokens = new HashMap<>();
        Algorithm algorithm = Algorithm.HMAC256(SECURITY_KEY);

        String access_token = JWT.create()
                .withSubject(users.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + X_DAY))
                .withIssuer(String.valueOf(users.getUsername().hashCode()))
                .withClaim("roles",  users.getRoles().stream().map(Roles::getRole).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(users.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Y_DAY))
                .withIssuer(String.valueOf(users.getUsername().hashCode()))
                .sign(algorithm);

        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        return tokens;
    }



    public  static Map<String,String> createJwtToken(User user){
        Map<String , String> tokens = new HashMap<>();
        Algorithm algorithm = Algorithm.HMAC256(SECURITY_KEY);

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + X_DAY))
                .withIssuer(String.valueOf(user.getUsername().hashCode()))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Y_DAY))
                .withIssuer(String.valueOf(user.getUsername().hashCode()))
                .sign(algorithm);

        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        return tokens;
    }


    public  ResponseDto<?> refreshTokens(HttpServletRequest request) {
        ResponseDto response = new ResponseDto();
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        Map<String, String> tokens = new HashMap<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(SECURITY_KEY.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();

                Users admin = usersService.getUsersByEmail(username).get();
                String access_token = JWT.create()
                        .withSubject(admin.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + X_DAY))
                        .withIssuer(String.valueOf((admin.getName() + admin.getSurname()).hashCode()))
                        .withClaim("roles", admin.getRoles().stream().map(Roles::getRole).collect(Collectors.toList()))
                        .sign(algorithm);

                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                response.setData(tokens);
                response.setIsError(false);

            } catch (Exception e) {
                log.error("Refresh token error ---------------->>>", e.getMessage());
                response.setIsError(true);
                response.setMessage("Refresh token error" + e.getMessage());
            }
        } else {
            throw new RuntimeException("Refresh token missing for some kind of exception");
        }

        return response;
    }
}
