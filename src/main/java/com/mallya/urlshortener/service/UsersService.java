package com.mallya.urlshortener.service;

import com.mallya.urlshortener.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public String getNameByUsername(String username) {
        return usersRepository.findById(username)
                .orElseThrow()
                .getName();
    }
}
