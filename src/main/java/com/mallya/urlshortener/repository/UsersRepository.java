package com.mallya.urlshortener.repository;

import com.mallya.urlshortener.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {}
