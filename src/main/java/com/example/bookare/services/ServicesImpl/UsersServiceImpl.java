package com.example.bookare.services.ServicesImpl;

import com.example.bookare.entities.Roles;
import com.example.bookare.entities.Users;
import com.example.bookare.entities.UsersReserve;
import com.example.bookare.repositories.RolesRepository;
import com.example.bookare.repositories.UsersRepository;
import com.example.bookare.services.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static com.example.bookare.utils.Constants.USERNAME_NOT_FOUND;

@Service
@AllArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String email) {

        Users admin = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, email)));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        admin.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getRole()))
        );

        return new User(admin.getUsername(), admin.getPassword(), authorities);
    }

    /**
     * TO-DO
     * Transactional should be implemented correctly
     */
    @Transactional
    @Override
    public Users castToUsers(UsersReserve reserve) {

        Users users = new Users();
        users.setEmail(reserve.getEmail());
        users.setName(reserve.getName());
        users.setSurname(reserve.getSurname());
        users.setPassword(reserve.getPassword());

        Optional<Roles> roles = rolesRepository.findByRole(reserve.getRole());

        if (roles.isPresent()) {
            users.setRoles(Collections.singletonList(roles.get()));
        } else {
            Roles role = new Roles();
            role.setRole(reserve.getRole());
            Roles roles1 = rolesRepository.save(role);
            users.setRoles(Collections.singletonList(roles1));
        }
        return users;
    }

    @Override
    public void save(Users users) {
        String password = users.getPassword();

        users.setPassword(passwordEncoder.encode(password));
        usersRepository.save(users);
    }
}
