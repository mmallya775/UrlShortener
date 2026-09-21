package com.mallya.urlshortener.repository;

import com.mallya.urlshortener.entity.ShortLinks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortLinksRepository extends JpaRepository<ShortLinks, Long> {
    List<ShortLinks> findByUsername(String username);
}
