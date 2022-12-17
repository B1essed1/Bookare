package com.example.bookare.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.bookare.entities.Roles;
import com.example.bookare.entities.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.bookare.utils.Constants.SECURITY_KEY;


public class JwtTokenCreator {
    public  static Map<String,String> createJwtToken(Users users){
        Map<String , String> tokens = new HashMap<>();
        Algorithm algorithm = Algorithm.HMAC256(SECURITY_KEY);

        String access_token = JWT.create()
                .withSubject(users.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24 * 7))
                .withIssuer(String.valueOf(users.getUsername().hashCode()))
                .withClaim("roles",  users.getRoles().stream().map(Roles::getRole).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(users.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 60 * 24 * 30))
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
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 24 * 7))
                .withIssuer(String.valueOf(user.getUsername().hashCode()))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000 * 60 * 24 * 30))
                .withIssuer(String.valueOf(user.getUsername().hashCode()))
                .sign(algorithm);

        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        return tokens;
    }
}
