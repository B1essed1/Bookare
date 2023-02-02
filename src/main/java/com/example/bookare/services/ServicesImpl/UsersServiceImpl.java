package com.example.bookare.services.ServicesImpl;

import com.example.bookare.entities.*;
import com.example.bookare.models.LoginDto;
import com.example.bookare.repositories.AddressRepository;
import com.example.bookare.repositories.RolesRepository;
import com.example.bookare.repositories.UsersRepository;
import com.example.bookare.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static com.example.bookare.utils.Constants.USERNAME_NOT_FOUND;

@Service
@Slf4j
    public class UsersServiceImpl implements UsersService, UserDetailsService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    public UsersServiceImpl(UsersRepository usersRepository, RolesRepository rolesRepository,
                            PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
    }


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

        Photo photo = reserve.getPhoto();
        users.setProfilePhoto(photo);




        if (roles.isPresent()) {
            users.setRoles(Collections.singletonList(roles.get()));
        } else {
            Roles role = new Roles();
            role.setRole(reserve.getRole());
            Roles roles1 = rolesRepository.save(role);
            users.setRoles(Collections.singletonList(roles1));
        }
        Address address = new Address();
        address.setDistrictName(reserve.getDistrictName());
        address.setRegionsName(reserve.getRegionsName());
        address.setQuartersNameKrill(reserve.getQuartersName());
        address.setUsers(users);
        addressRepository.save(address);
        return users;
    }

    @Override
    public void save(Users users) {
        String password = users.getPassword();

        users.setPassword(passwordEncoder.encode(password));
        usersRepository.save(users);
    }

    @Override
    public Optional<Users> getUsersByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Optional<Users> findUserByEmailAndPassword(LoginDto loginDto) {
        Optional<Users> loggedInUser  = usersRepository
                .findByEmailAndPassword(loginDto.getEmail(), passwordEncoder.encode(loginDto.getPassword()));
        return loggedInUser;
    }
}
