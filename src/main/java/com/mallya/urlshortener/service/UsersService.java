package com.mallya.urlshortener.service;

import com.mallya.urlshortener.dto.NewUserRequestDTO;
import com.mallya.urlshortener.entity.Users;
import com.mallya.urlshortener.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public String getNameByUsername(String username) {
        return usersRepository.findById(username).orElseThrow().getName();
    }

    public void createNewUser(NewUserRequestDTO newUserRequestDTO) {
        if (usersRepository.existsById(newUserRequestDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Users user = new Users();

        user.setUsername(newUserRequestDTO.getUsername());
        user.setName(newUserRequestDTO.getName());
        user.setPassword(passwordEncoder.encode(newUserRequestDTO.getPassword()));
        user.setEnabled(true);

        user.getAuthorities().add("ROLE_USER");

        usersRepository.save(user);
    }
}
