//package com.example.bookare.component;
//
//import com.example.bookare.entities.Ratings;
//import com.example.bookare.entities.Roles;
//import com.example.bookare.entities.Users;
//import com.example.bookare.repositories.RatingsRepository;
//import com.example.bookare.repositories.RolesRepository;
//import com.example.bookare.repositories.UsersRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
//@Component
//@RequiredArgsConstructor
//public class Dataloader implements CommandLineRunner {
//
//    private final UsersRepository usersRepository;
//    private final RolesRepository rolesRepository;
//    private final RatingsRepository ratingRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Value("${spring.sql.init.mode}")
//    private String mode;
//
//    @Override
//    public void run(String... args) {
//        if (mode.equals("always")) {
//            List<Users> users = new ArrayList<>();
//            Roles role = rolesRepository.save(new Roles(1L, "USER", users));
//
//            List<Roles> roles = Collections.singletonList(new Roles());
//            Users user1 = usersRepository.save(new Users(1L, "Tester", "Testerov", passwordEncoder.encode("123"), "test1@gmail.com", roles));
//            Users user2 = usersRepository.save(new Users(2L, "Tester", "Testerov", passwordEncoder.encode("123"), "test2@gmail.com", roles));
//
//            users.add(user1);
//            users.add(user2);
//
//            Ratings rating1 = ratingRepository.save(new Ratings(1L, 4, "test1", user1, user2, new Date()));
//            Ratings rating2 = ratingRepository.save(new Ratings(2L, 4, "test2", user1, user2, new Date()));
//            Ratings rating3 = ratingRepository.save(new Ratings(3L, 3, "test3", user1, user2, new Date()));
//        }
//    }
//}