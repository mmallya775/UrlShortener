package com.mallya.urlshortener.service;

import com.mallya.urlshortener.dto.NewUserRequestDTO;
import com.mallya.urlshortener.entity.Users;
import com.mallya.urlshortener.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public String getNameByUsername(String username) {
        return usersRepository.findById(username).orElseThrow().getName();
    }

    public void createNewUser(NewUserRequestDTO newUserRequestDTO) {
        if (usersRepository.existsById(newUserRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        userDetailsManager.createUser(User.builder()
                .username(newUserRequestDTO.getUsername())
                .password(passwordEncoder.encode(newUserRequestDTO.getPassword()))
                .roles("USER")
                .build());
        Users user = usersRepository.findById(newUserRequestDTO.getUsername()).orElseThrow();

        user.setName(newUserRequestDTO.getName());

        usersRepository.save(user);
    }
}
