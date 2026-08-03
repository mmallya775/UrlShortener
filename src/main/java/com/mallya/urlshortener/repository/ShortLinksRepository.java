package com.mallya.urlshortener.repository;

import com.mallya.urlshortener.entity.ShortLinks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortLinksRepository extends JpaRepository<ShortLinks, Long> {
}
    